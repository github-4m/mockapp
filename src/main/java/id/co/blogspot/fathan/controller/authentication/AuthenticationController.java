package id.co.blogspot.fathan.controller.authentication;

import id.co.blogspot.fathan.dto.AuthenticateRequest;
import id.co.blogspot.fathan.dto.BaseResponse;
import id.co.blogspot.fathan.dto.RegisterRequest;
import id.co.blogspot.fathan.dto.SingleBaseResponse;
import id.co.blogspot.fathan.entity.User;
import id.co.blogspot.fathan.service.user.UserService;
import id.co.blogspot.fathan.util.Precondition;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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

  @RequestMapping(value = AuthenticationControllerPath.LOGIN, method = RequestMethod.POST
          , consumes = {MediaType.APPLICATION_JSON_VALUE})
  public SingleBaseResponse<String> authenticate(@RequestParam String requestId, @RequestBody AuthenticateRequest
          request)
          throws
          Exception {
    Precondition.checkArgument(!StringUtils.isEmpty(request.getUsername()), AuthenticationControllerErrorMessage.USERNAME_MUST_NOT_BE_BLANK);
    Precondition.checkArgument(!StringUtils.isEmpty(request.getPassword()), AuthenticationControllerErrorMessage.PASSWORD_MUST_NOT_BE_BLANK);
    String jwtToken = this.userService.authenticate(request.getUsername(), request.getPassword());
    return new SingleBaseResponse<String>(null, null, true, requestId, jwtToken);
  }

  @RequestMapping(value = AuthenticationControllerPath.SIGNUP, method = RequestMethod.POST
          , consumes = {MediaType.APPLICATION_JSON_VALUE})
  public BaseResponse register(@RequestParam String requestId, @RequestBody RegisterRequest request) throws Exception {
    Precondition.checkArgument(!StringUtils.isEmpty(request.getUsername()), AuthenticationControllerErrorMessage.USERNAME_MUST_NOT_BE_BLANK);
    Precondition.checkArgument(!StringUtils.isEmpty(request.getPassword()), AuthenticationControllerErrorMessage.PASSWORD_MUST_NOT_BE_BLANK);
    User user = this.generateUser(request);
    this.userService.register(user);
    return new BaseResponse(null, null, true, requestId);
  }

}
