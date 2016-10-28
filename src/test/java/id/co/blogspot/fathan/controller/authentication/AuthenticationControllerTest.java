package id.co.blogspot.fathan.controller.authentication;

import id.co.blogspot.fathan.dto.AuthenticateRequest;
import id.co.blogspot.fathan.dto.RegisterRequest;
import id.co.blogspot.fathan.entity.User;
import id.co.blogspot.fathan.service.user.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

/**
 * Created by fathan.mustaqiim on 10/28/2016.
 */
public class AuthenticationControllerTest {

  private static final String DEFAULT_USERNAME_1 = "DEVELOPER";
  private static final String DEFAULT_PASSWORD_1 = UUID.randomUUID().toString();
  private static final String DEFAULT_REQUEST_ID = UUID.randomUUID().toString();

  @Mock
  private UserService userService;

  @InjectMocks
  private AuthenticationController authenticationController;

  private AuthenticateRequest generateAuthenticateRequest() throws Exception {
    AuthenticateRequest authenticateRequest = new AuthenticateRequest();
    authenticateRequest.setUsername(AuthenticationControllerTest.DEFAULT_USERNAME_1);
    authenticateRequest.setPassword(AuthenticationControllerTest.DEFAULT_PASSWORD_1);
    return authenticateRequest;
  }

  private RegisterRequest generateRegisterRequest() throws Exception {
    RegisterRequest registerRequest = new RegisterRequest();
    registerRequest.setUsername(AuthenticationControllerTest.DEFAULT_USERNAME_1);
    registerRequest.setPassword(AuthenticationControllerTest.DEFAULT_PASSWORD_1);
    return registerRequest;
  }

  @Before
  public void initializeTest() throws Exception {
    MockitoAnnotations.initMocks(this);
    Mockito.when(this.userService.authenticate(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
    Mockito.doNothing().when(this.userService).register((User) Mockito.anyObject());
  }

  @After
  public void finalizeTest() throws Exception {
    Mockito.verifyNoMoreInteractions(this.userService);
  }

  @Test
  public void constructorTest() throws Exception {
    AuthenticationControllerPath authenticationControllerPath = new AuthenticationControllerPath();
    AuthenticationControllerErrorMessage authenticationControllerErrorMessage = new
            AuthenticationControllerErrorMessage();
  }

  @Test
  public void authenticateTest() throws Exception {
    AuthenticateRequest request = this.generateAuthenticateRequest();
    this.authenticationController.authenticate(AuthenticationControllerTest.DEFAULT_REQUEST_ID, request);
    Mockito.verify(this.userService).authenticate(Mockito.anyString(), Mockito.anyString());
  }

  @Test
  public void registerTest() throws Exception {
    RegisterRequest request = this.generateRegisterRequest();
    this.authenticationController.register(AuthenticationControllerTest.DEFAULT_REQUEST_ID, request);
    Mockito.verify(this.userService).register((User) Mockito.anyObject());
  }

}
