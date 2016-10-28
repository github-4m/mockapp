package id.co.blogspot.fathan.service.user;

import id.co.blogspot.fathan.entity.User;
import id.co.blogspot.fathan.repository.user.UserRepository;
import id.co.blogspot.fathan.service.session.SessionService;
import id.co.blogspot.fathan.util.Credential;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.UUID;

/**
 * Created by fathan.mustaqiim on 10/24/2016.
 */
@Service
@Transactional(readOnly = true)
public class UserServiceBean implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private SessionService sessionService;

  @Value("${jwt.secret.key}")
  private String jwtSecretKey;

  private String generatePassword(String password) throws Exception {
    MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
    String digestedPassword =
            (new HexBinaryAdapter()).marshal(messageDigest.digest(password.getBytes(StandardCharsets.UTF_8)));
    return digestedPassword;
  }

  @Override
  @Transactional(readOnly = false, rollbackFor = Exception.class)
  public String authenticate(String username, String password) throws Exception {
    String digestedPassword = this.generatePassword(password);
    User user = this.userRepository.findByUsernameAndPasswordAndMarkForDeleteFalse(username, digestedPassword);
    if (user == null) {
      throw new BadCredentialsException("Invalid username or password");
    }
    this.sessionService.create(username);
    return this.generateJwtToken(user);
  }

  @Override
  public String generateJwtToken(User user) throws Exception {
    Claims claims = Jwts.claims().setSubject(user.getUsername());
    claims.put("sessionId", Credential.getSessionId());
    return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, this.jwtSecretKey).compact();
  }

  @Override
  public Claims parseJwtToken(String jwtToken) {
    try {
      Claims claims = Jwts.parser().setSigningKey(this.jwtSecretKey).parseClaimsJws(jwtToken).getBody();
      return claims;
    } catch (JwtException | ClassCastException e) {
      return null;
    }
  }

  @Override
  @Transactional(readOnly = false, rollbackFor = Exception.class)
  public void register(User user) throws Exception {
    User savedUser = this.userRepository.findByUsername(user.getUsername());
    if (savedUser != null) {
      throw new Exception("Username already used");
    }
    user.setPassword(this.generatePassword(user.getPassword()));
    this.userRepository.save(user);
  }

  @Override
  @Transactional(readOnly = false, rollbackFor = Exception.class)
  public void unauthenticate() throws Exception {
    this.sessionService.remove();
  }
}
