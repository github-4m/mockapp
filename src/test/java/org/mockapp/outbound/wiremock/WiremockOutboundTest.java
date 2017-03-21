package org.mockapp.outbound.wiremock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockapp.outbound.HttpInvoker;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Created by fathan.mustaqim on 3/17/2017.
 */
public class WiremockOutboundTest {

  private static final Integer DEFAULT_PORT = 80;

  @Mock
  private HttpInvoker httpInvoker;

  @InjectMocks
  private WiremockOutboundBean wiremockOutboundBean;

  @Before
  public void initializeTest() throws Exception {
    MockitoAnnotations.initMocks(this);
    ReflectionTestUtils.setField(this.wiremockOutboundBean, "wiremockPort",
        String.valueOf(WiremockOutboundTest.DEFAULT_PORT));
    Mockito.when(this.httpInvoker
        .invoke(Mockito.any(HttpMethod.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(null);
    Mockito.when(this.httpInvoker
        .invoke(Mockito.any(HttpMethod.class), Mockito.any(HttpServletRequest.class),
            Mockito.any(HttpServletResponse.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(null);
  }

  @After
  public void finalizeTest() throws Exception {
    Mockito.verifyNoMoreInteractions(this.httpInvoker);
  }

  @Test
  public void resetTest() throws Exception {
    this.wiremockOutboundBean.reset();
    Mockito.verify(this.httpInvoker)
        .invoke(Mockito.any(HttpMethod.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.anyInt(), Mockito.anyString(), Mockito.anyString());
  }

  @Test
  public void mockupTest() throws Exception {
    this.wiremockOutboundBean.mockup(HttpMethod.GET, null, null, null, null);
    Mockito.verify(this.httpInvoker)
        .invoke(Mockito.any(HttpMethod.class), Mockito.any(HttpServletRequest.class),
            Mockito.any(HttpServletResponse.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.anyInt(), Mockito.anyString(), Mockito.anyString());
  }

}
