package id.co.blogspot.fathan.controller.authentication;

import id.co.blogspot.fathan.dto.AuthenticateRequest;
import id.co.blogspot.fathan.dto.RegisterRequest;
import id.co.blogspot.fathan.entity.User;
import id.co.blogspot.fathan.service.user.UserService;
import java.util.UUID;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.verification.VerificationMode;

/**
 * Created by fathan.mustaqiim on 10/28/2016.
 */
public class AuthenticationControllerTest {

  private static final String DEFAULT_USERNAME_1 = "DEVELOPER";
  private static final String DEFAULT_PASSWORD_1 = UUID.randomUUID().toString();
  private static final String DEFAULT_NAME_1 = "DEVELOPER";
  private static final String DEFAULT_EMAIL_1 = "developer@developer.org";
  private static final String DEFAULT_REQUEST_ID = UUID.randomUUID().toString();
  private static final VerificationMode NEVER_CALLED = Mockito.times(0);

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
    registerRequest.setName(AuthenticationControllerTest.DEFAULT_NAME_1);
    registerRequest.setEmail(AuthenticationControllerTest.DEFAULT_EMAIL_1);
    return registerRequest;
  }

  @Before
  public void initializeTest() throws Exception {
    MockitoAnnotations.initMocks(this);
    Mockito.when(this.userService.authenticate(Mockito.anyString(), Mockito.anyString()))
        .thenReturn(null);
    Mockito.doNothing().when(this.userService).register((User) Mockito.anyObject());
    Mockito.doNothing().when(this.userService).unauthenticate();
  }

  @After
  public void finalizeTest() throws Exception {
    Mockito.verifyNoMoreInteractions(this.userService);
  }

  @Test
  public void authenticateTest() throws Exception {
    AuthenticateRequest request = this.generateAuthenticateRequest();
    this.authenticationController.authenticate(
        AuthenticationControllerTest.DEFAULT_REQUEST_ID, request);
    Mockito.verify(this.userService).authenticate(Mockito.anyString(), Mockito.anyString());
  }

  @Test(expected = Exception.class)
  public void authenticateWithUsernameExceptionTest() throws Exception {
    AuthenticateRequest request = this.generateAuthenticateRequest();
    request.setUsername(null);
    try {
      this.authenticationController.authenticate(
          AuthenticationControllerTest.DEFAULT_REQUEST_ID, request);
    } catch (Exception e) {
      Assert.assertEquals(
          AuthenticationControllerErrorMessage.USERNAME_MUST_NOT_BE_BLANK, e.getMessage());
      Mockito.verify(this.userService, AuthenticationControllerTest.NEVER_CALLED)
          .authenticate(Mockito.anyString(), Mockito.anyString());
      throw e;
    }
  }

  @Test(expected = Exception.class)
  public void authenticateWithPasswordExceptionTest() throws Exception {
    AuthenticateRequest request = this.generateAuthenticateRequest();
    request.setPassword(null);
    try {
      this.authenticationController.authenticate(
          AuthenticationControllerTest.DEFAULT_REQUEST_ID, request);
    } catch (Exception e) {
      Assert.assertEquals(
          AuthenticationControllerErrorMessage.PASSWORD_MUST_NOT_BE_BLANK, e.getMessage());
      Mockito.verify(this.userService, AuthenticationControllerTest.NEVER_CALLED)
          .authenticate(Mockito.anyString(), Mockito.anyString());
      throw e;
    }
  }

  @Test
  public void registerTest() throws Exception {
    RegisterRequest request = this.generateRegisterRequest();
    this.authenticationController.register(
        AuthenticationControllerTest.DEFAULT_REQUEST_ID, request);
    Mockito.verify(this.userService).register((User) Mockito.anyObject());
  }

  @Test(expected = Exception.class)
  public void registerWithUsernameExceptionTest() throws Exception {
    RegisterRequest request = this.generateRegisterRequest();
    request.setUsername(null);
    try {
      this.authenticationController.register(
          AuthenticationControllerTest.DEFAULT_REQUEST_ID, request);
    } catch (Exception e) {
      Assert.assertEquals(
          AuthenticationControllerErrorMessage.USERNAME_MUST_NOT_BE_BLANK, e.getMessage());
      Mockito.verify(this.userService, AuthenticationControllerTest.NEVER_CALLED)
          .register((User) Mockito.anyObject());
      throw e;
    }
  }

  @Test(expected = Exception.class)
  public void registerWithPasswordExceptionTest() throws Exception {
    RegisterRequest request = this.generateRegisterRequest();
    request.setPassword(null);
    try {
      this.authenticationController.register(
          AuthenticationControllerTest.DEFAULT_REQUEST_ID, request);
    } catch (Exception e) {
      Assert.assertEquals(
          AuthenticationControllerErrorMessage.PASSWORD_MUST_NOT_BE_BLANK, e.getMessage());
      Mockito.verify(this.userService, AuthenticationControllerTest.NEVER_CALLED)
          .register((User) Mockito.anyObject());
      throw e;
    }
  }

  @Test(expected = Exception.class)
  public void registerWithNameExceptionTest() throws Exception {
    RegisterRequest request = this.generateRegisterRequest();
    request.setName(null);
    try {
      this.authenticationController.register(
          AuthenticationControllerTest.DEFAULT_REQUEST_ID, request);
    } catch (Exception e) {
      Assert.assertEquals(
          AuthenticationControllerErrorMessage.NAME_MUST_NOT_BE_BLANK, e.getMessage());
      Mockito.verify(this.userService, AuthenticationControllerTest.NEVER_CALLED)
          .register((User) Mockito.anyObject());
      throw e;
    }
  }

  @Test(expected = Exception.class)
  public void registerWithEmailExceptionTest() throws Exception {
    RegisterRequest request = this.generateRegisterRequest();
    request.setEmail(null);
    try {
      this.authenticationController.register(
          AuthenticationControllerTest.DEFAULT_REQUEST_ID, request);
    } catch (Exception e) {
      Assert.assertEquals(
          AuthenticationControllerErrorMessage.EMAIL_MUST_NOT_BE_BLANK, e.getMessage());
      Mockito.verify(this.userService, AuthenticationControllerTest.NEVER_CALLED)
          .register((User) Mockito.anyObject());
      throw e;
    }
  }

  @Test
  public void unauthenticateTest() throws Exception {
    this.authenticationController.unauthenticate(AuthenticationControllerTest.DEFAULT_REQUEST_ID);
    Mockito.verify(this.userService).unauthenticate();
  }
}
