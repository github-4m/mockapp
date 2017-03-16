package org.mockapp.controller.authentication;

import org.mockapp.dto.BaseResponse;
import org.mockapp.dto.SingleBaseResponse;
import org.mockapp.dto.authentication.AuthenticateRequest;
import org.mockapp.dto.authentication.RegisterRequest;
import org.mockapp.entity.User;
import org.mockapp.service.user.UserService;
import org.mockapp.util.Precondition;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by fathan.mustaqiim on 10/24/2016.
 */
@RestController
@RequestMapping(value = AuthenticationControllerPath.BASE_PATH)
public class AuthenticationController {

  @Autowired
  private UserService userService;

  private User generateUser(RegisterRequest request) throws Exception {
    User user = new User();
    BeanUtils.copyProperties(request, user);
    return user;
  }

  @RequestMapping(
      value = AuthenticationControllerPath.LOGIN,
      method = RequestMethod.POST,
      consumes = {MediaType.APPLICATION_JSON_VALUE}
  )
  public SingleBaseResponse<String> authenticate(
      @RequestParam String requestId, @RequestBody AuthenticateRequest request) throws Exception {
    Precondition.checkArgument(
        !StringUtils.isEmpty(request.getUsername()),
        AuthenticationControllerErrorMessage.USERNAME_MUST_NOT_BE_BLANK);
    Precondition.checkArgument(
        !StringUtils.isEmpty(request.getPassword()),
        AuthenticationControllerErrorMessage.PASSWORD_MUST_NOT_BE_BLANK);
    String jwtToken = this.userService.authenticate(request.getUsername(), request.getPassword());
    return new SingleBaseResponse<String>(null, null, true, requestId, jwtToken);
  }

  @RequestMapping(
      value = AuthenticationControllerPath.SIGNUP,
      method = RequestMethod.POST,
      consumes = {MediaType.APPLICATION_JSON_VALUE}
  )
  public BaseResponse register(@RequestParam String requestId, @RequestBody RegisterRequest request)
      throws Exception {
    Precondition.checkArgument(
        !StringUtils.isEmpty(request.getUsername()),
        AuthenticationControllerErrorMessage.USERNAME_MUST_NOT_BE_BLANK);
    Precondition.checkArgument(
        !StringUtils.isEmpty(request.getPassword()),
        AuthenticationControllerErrorMessage.PASSWORD_MUST_NOT_BE_BLANK);
    Precondition.checkArgument(
        !StringUtils.isEmpty(request.getName()),
        AuthenticationControllerErrorMessage.NAME_MUST_NOT_BE_BLANK);
    Precondition.checkArgument(
        !StringUtils.isEmpty(request.getEmail()),
        AuthenticationControllerErrorMessage.EMAIL_MUST_NOT_BE_BLANK);
    User user = this.generateUser(request);
    this.userService.register(user);
    return new BaseResponse(null, null, true, requestId);
  }

  @RequestMapping(value = AuthenticationControllerPath.LOGOUT, method = RequestMethod.GET)
  public BaseResponse unauthenticate(@RequestParam String requestId) throws Exception {
    this.userService.unauthenticate();
    return new BaseResponse(null, null, true, requestId);
  }
}
