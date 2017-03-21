package org.mockapp.outbound;

import java.net.URI;
import java.util.Arrays;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Created by 4than.mustaqiim on 3/16/2017.
 */
public class HttpInvokerTest {

  private static final String DEFAULT_SCHEME = "http";
  private static final String DEFAULT_HOST = "localhost";
  private static final Integer DEFAULT_PORT = 80;
  private static final String DEFAULT_PATH = "/";
  private static final String DEFAULT_REQUEST_ID = UUID.randomUUID().toString();

  @Mock
  private RestTemplate restTemplate;

  @InjectMocks
  private HttpInvoker httpInvoker;

  private ResponseEntity<String> generateResponseEntity() throws Exception {
    MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
    headers.put(HttpHeaders.CONTENT_TYPE,
        Arrays.asList(new String[]{MediaType.APPLICATION_JSON_VALUE}));
    ResponseEntity<String> responseEntity = new ResponseEntity<String>(headers, HttpStatus.OK);
    return responseEntity;
  }

  private HttpServletRequest generateHttpServletRequest() throws Exception {
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    request.addParameter("requestId", HttpInvokerTest.DEFAULT_REQUEST_ID);
    return request;
  }

  private HttpServletResponse generateHttpServletResponse() throws Exception {
    MockHttpServletResponse response = new MockHttpServletResponse();
    return response;
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
  public void invoke_1_Test() throws Exception {
    this.httpInvoker
        .invoke(HttpMethod.GET, HttpInvokerTest.DEFAULT_SCHEME, HttpInvokerTest.DEFAULT_HOST,
            HttpInvokerTest.DEFAULT_PORT, HttpInvokerTest.DEFAULT_PATH, null);
    Mockito.verify(this.restTemplate)
        .exchange(Mockito.any(URI.class), Mockito.any(HttpMethod.class),
            Mockito.any(HttpEntity.class), Mockito.any(Class.class));
  }

  @Test
  public void invoke_2_Test() throws Exception {
    this.httpInvoker.invoke(HttpMethod.GET, this.generateHttpServletRequest(),
        this.generateHttpServletResponse(), HttpInvokerTest.DEFAULT_SCHEME,
        HttpInvokerTest.DEFAULT_HOST, HttpInvokerTest.DEFAULT_PORT, HttpInvokerTest.DEFAULT_PATH,
        null);
    Mockito.verify(this.restTemplate)
        .exchange(Mockito.any(URI.class), Mockito.any(HttpMethod.class),
            Mockito.any(HttpEntity.class), Mockito.any(Class.class));
  }

}
