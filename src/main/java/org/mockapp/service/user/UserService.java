package org.mockapp.service.user;

import io.jsonwebtoken.Claims;
import org.mockapp.entity.User;

/**
 * Created by fathan.mustaqiim on 10/24/2016.
 */
public interface UserService {

  String authenticate(String username, String password) throws Exception;

  String generateJwtToken(User user) throws Exception;

  Claims parseJwtToken(String jwtToken);

  void register(User user) throws Exception;

  void unauthenticate() throws Exception;
}
