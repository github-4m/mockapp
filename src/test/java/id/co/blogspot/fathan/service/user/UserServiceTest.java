package id.co.blogspot.fathan.service.user;

import id.co.blogspot.fathan.entity.User;
import id.co.blogspot.fathan.repository.user.UserRepository;
import id.co.blogspot.fathan.service.session.SessionService;
import id.co.blogspot.fathan.util.Credential;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.verification.VerificationMode;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.UUID;

/**
 * Created by fathan.mustaqiim on 10/27/2016.
 */
public class UserServiceTest {

  private static final String DEFAULT_USERNAME_1 = "DEVELOPER";
  private static final String DEFAULT_USERNAME_2 = "DEVELOPER_2";
  private static final String DEFAULT_PASSWORD_1 = UUID.randomUUID().toString();
  private static final String DEFAULT_SESSION_ID_1 = UUID.randomUUID().toString();
  private static final VerificationMode NEVER_CALLED = Mockito.times(0);

  @Mock
  private UserRepository userRepository;

  @Mock
  private SessionService sessionService;

  @InjectMocks
  private UserServiceBean userServiceBean;

  private String jwtToken;

  private void generateJwtSecretKey() throws Exception {
    ReflectionTestUtils.setField(this.userServiceBean, "jwtSecretKey", UUID.randomUUID().toString());
  }

  private User generateUser() throws Exception {
    User user = new User();
    return user;
  }

  @Before
  public void initializeTest() throws Exception {
    MockitoAnnotations.initMocks(this);
    this.generateJwtSecretKey();
    User user = this.generateUser();
    Credential.setSessionId(UserServiceTest.DEFAULT_SESSION_ID_1);
    this.jwtToken = this.userServiceBean.generateJwtToken(user);
    Mockito.when(this.userRepository.findByUsernameAndPasswordAndMarkForDeleteFalse(Mockito.eq(UserServiceTest.DEFAULT_USERNAME_1),
            Mockito.anyString())).thenReturn(user);
    Mockito.when(this.userRepository.findByUsernameAndPasswordAndMarkForDeleteFalse(Mockito.eq(UserServiceTest
            .DEFAULT_USERNAME_2), Mockito.anyString())).thenReturn(null);
    Mockito.doNothing().when(this.sessionService).create(Mockito.anyString());
    Mockito.when(this.userRepository.findByUsername(Mockito.eq(UserServiceTest.DEFAULT_USERNAME_1))).thenReturn(null);
    Mockito.when(this.userRepository.findByUsername(Mockito.eq(UserServiceTest.DEFAULT_USERNAME_2))).thenReturn(user);
    Mockito.when(this.userRepository.save((User) Mockito.anyObject())).thenReturn(null);
  }

  @After
  public void finalizeTest() throws Exception {
    Mockito.verifyNoMoreInteractions(this.userRepository);
    Mockito.verifyNoMoreInteractions(this.sessionService);
  }

  @Test
  public void authenticateTest() throws Exception {
    this.userServiceBean.authenticate(UserServiceTest.DEFAULT_USERNAME_1, UserServiceTest.DEFAULT_PASSWORD_1);
    Mockito.verify(this.userRepository)
            .findByUsernameAndPasswordAndMarkForDeleteFalse(Mockito.eq(UserServiceTest.DEFAULT_USERNAME_1),
                    Mockito.anyString());
    Mockito.verify(this.sessionService).create(Mockito.anyString());
  }

  @Test(expected = BadCredentialsException.class)
  public void authenticateWithExceptionTest() throws Exception {
    try {
      this.userServiceBean.authenticate(UserServiceTest.DEFAULT_USERNAME_2, UserServiceTest.DEFAULT_PASSWORD_1);
    } catch
            (Exception e) {
      Mockito.verify(this.userRepository)
              .findByUsernameAndPasswordAndMarkForDeleteFalse(Mockito.eq(UserServiceTest.DEFAULT_USERNAME_2),
                      Mockito.anyString());
      Mockito.verify(this.sessionService, UserServiceTest.NEVER_CALLED).create(Mockito.anyString());
      throw e;
    }
  }

  @Test
  public void parseJwtTokenTest() throws Exception {
    this.userServiceBean.parseJwtToken(this.jwtToken);
  }

  @Test
  public void parseJwtTokenWithNullReturnTest() throws Exception {
    this.userServiceBean.parseJwtToken(UUID.randomUUID().toString());
  }

  @Test
  public void registerTest() throws Exception {
    User user = new User();
    user.setUsername(UserServiceTest.DEFAULT_USERNAME_1);
    user.setPassword(UserServiceTest.DEFAULT_PASSWORD_1);
    this.userServiceBean.register(user);
    Mockito.verify(this.userRepository).findByUsername(Mockito.eq(UserServiceTest.DEFAULT_USERNAME_1));
    Mockito.verify(this.userRepository).save((User) Mockito.anyObject());
  }

  @Test(expected = Exception.class)
  public void registerWithExceptionTest() throws Exception {
    try {
      User user = new User();
      user.setUsername(UserServiceTest.DEFAULT_USERNAME_2);
      user.setPassword(UserServiceTest.DEFAULT_PASSWORD_1);
      this.userServiceBean.register(user);
    } catch (Exception e) {
      Mockito.verify(this.userRepository).findByUsername(Mockito.eq(UserServiceTest.DEFAULT_USERNAME_2));
      Mockito.verify(this.userRepository, UserServiceTest.NEVER_CALLED).save((User) Mockito.anyObject());
      throw e;
    }
  }

}
