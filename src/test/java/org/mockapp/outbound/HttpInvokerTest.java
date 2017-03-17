package org.mockapp.outbound;

import java.net.URI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Created by 4than.mustaqiim on 3/16/2017.
 */
public class HttpInvokerTest {

  private static final String DEFAULT_SCHEME = "http";
  private static final String DEFAULT_HOST = "localhost";
  private static final Integer DEFAULT_PORT = 80;
  private static final String DEFAULT_PATH = "/";

  @Mock
  private RestTemplate restTemplate;

  @InjectMocks
  private HttpInvoker httpInvoker;

  private ResponseEntity<String> generateResponseEntity() throws Exception {
    ResponseEntity<String> responseEntity = new ResponseEntity<String>(HttpStatus.OK);
    return responseEntity;
  }

  @Before
  public void initializeTest() throws Exception {
    MockitoAnnotations.initMocks(this);
    ResponseEntity<String> responseEntity = this.generateResponseEntity();
    Mockito.when(this.restTemplate.exchange(Mockito.any(URI.class), Mockito.any(HttpMethod.class),
        Mockito.any(HttpEntity.class), Mockito.any(Class.class))).thenReturn(responseEntity);
  }

  @After
  public void finalizeTest() throws Exception {
    Mockito.verifyNoMoreInteractions(this.restTemplate);
  }

  @Test
  public void invokeTest() throws Exception {
    this.httpInvoker
        .invoke(HttpMethod.GET, HttpInvokerTest.DEFAULT_SCHEME, HttpInvokerTest.DEFAULT_HOST,
            HttpInvokerTest.DEFAULT_PORT, HttpInvokerTest.DEFAULT_PATH, null);
    Mockito.verify(this.restTemplate)
        .exchange(Mockito.any(URI.class), Mockito.any(HttpMethod.class),
            Mockito.any(HttpEntity.class), Mockito.any(Class.class));
  }

}
