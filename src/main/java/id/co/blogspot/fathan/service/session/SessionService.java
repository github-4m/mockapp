package id.co.blogspot.fathan.service.session;

import id.co.blogspot.fathan.security.exception.UnauthorizedException;

/**
 * Created by fathan.mustaqiim on 10/27/2016.
 */
public interface SessionService {

  void create(String username) throws Exception;

  boolean isAuthorized() throws Exception;

  void remove() throws Exception;

}
