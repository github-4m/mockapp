package id.co.blogspot.fathan.service.user;

import id.co.blogspot.fathan.entity.User;

/**
 * Created by fathan.mustaqiim on 10/24/2016.
 */
public interface UserService {

  String authenticate(String username, String password) throws Exception;

  String generateJwtToken(User user) throws Exception;

  User parseJwtToken(String jwtToken);

  void register(User user) throws Exception;

}
