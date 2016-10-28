package id.co.blogspot.fathan.service.session;

import id.co.blogspot.fathan.entity.Session;
import id.co.blogspot.fathan.repository.session.SessionRepository;
import id.co.blogspot.fathan.util.Credential;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.verification.VerificationMode;

import java.util.UUID;

/**
 * Created by fathan.mustaqiim on 10/27/2016.
 */
public class SessionServiceTest {

  private static final String DEFAULT_USERNAME_1 = "DEVELOPER";
  private static final String DEFAULT_SESSION_ID_1 = UUID.randomUUID().toString();
  private static final VerificationMode NEVER_CALLED = Mockito.times(0);

  @Mock
  private SessionRepository sessionRepository;

  @InjectMocks
  private SessionServiceBean sessionServiceBean;

  private Session generateSession() throws Exception {
    Session session = new Session();
    return session;
  }

  @Before
  public void initializeTest() throws Exception {
    MockitoAnnotations.initMocks(this);
    Session session = this.generateSession();
    Credential.setUsername(SessionServiceTest.DEFAULT_USERNAME_1);
    Credential.setSessionId(SessionServiceTest.DEFAULT_SESSION_ID_1);
    Mockito.when(this.sessionRepository.findByUsernameAndSessionId(Mockito.anyString(), Mockito.anyString()))
            .thenReturn(session);
    Mockito.when(this.sessionRepository.findByUsername(Mockito.eq(SessionServiceTest.DEFAULT_USERNAME_1))).thenReturn
            (session);
    Mockito.doNothing().when(this.sessionRepository).delete(Mockito.anyString());
    Mockito.doNothing().when(this.sessionRepository).flush();
    Mockito.when(this.sessionRepository.save((Session) Mockito.anyObject())).thenReturn(null);
  }

  @After
  public void finalizeTest() throws Exception {
    Mockito.verifyNoMoreInteractions(this.sessionRepository);
  }

  @Test
  public void isAuthorizedTest() throws Exception {
    this.sessionServiceBean.isAuthorized();
    Mockito.verify(this.sessionRepository).findByUsernameAndSessionId(Mockito.anyString(), Mockito.anyString());
  }

  @Test
  public void createTest() throws Exception {
    this.sessionServiceBean.create(SessionServiceTest.DEFAULT_USERNAME_1);
    Mockito.verify(this.sessionRepository).findByUsername(Mockito.eq(SessionServiceTest.DEFAULT_USERNAME_1));
    Mockito.verify(this.sessionRepository).delete(Mockito.anyString());
    Mockito.verify(this.sessionRepository).flush();
    Mockito.verify(this.sessionRepository).save((Session) Mockito.anyObject());
  }

  @Test(expected = Exception.class)
  public void createWithExceptionTest() throws Exception {
    try {
      this.sessionServiceBean.create(null);
    } catch (Exception e) {
      Mockito.verify(this.sessionRepository, SessionServiceTest.NEVER_CALLED)
              .findByUsername(Mockito.eq(SessionServiceTest
                      .DEFAULT_USERNAME_1));
      Mockito.verify(this.sessionRepository, SessionServiceTest.NEVER_CALLED).delete(Mockito.anyString());
      Mockito.verify(this.sessionRepository, SessionServiceTest.NEVER_CALLED).flush();
      Mockito.verify(this.sessionRepository, SessionServiceTest.NEVER_CALLED).save((Session) Mockito.anyObject());
      throw e;
    }
  }

}
