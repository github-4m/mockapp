package org.mockapp.controller.endpoint;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockapp.dto.endpoint.CreateEndpointRequest;
import org.mockapp.entity.Endpoint;
import org.mockapp.service.endpoint.EndpointService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.verification.VerificationMode;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by fathan.mustaqim on 3/20/2017.
 */
public class EndpointControllerTest {

  private static final String DEFAULT_REQUEST_ID = UUID.randomUUID().toString();
  private static final String DEFAULT_MODULE_CODE = UUID.randomUUID().toString();
  private static final String DEFAULT_URL = "/test";
  private static final String DEFAULT_REQUEST_URI = "/mockup/test";
  private static final VerificationMode CALLED_TWICE = Mockito.times(2);
  private static final VerificationMode NEVER_CALLED = Mockito.times(0);

  @Mock
  private ObjectMapper objectMapper;

  @Mock
  private EndpointService endpointService;

  @InjectMocks
  private EndpointController endpointController;

  private Endpoint generateEndpoint() throws Exception {
    Endpoint endpoint = new Endpoint();
    endpoint.setUrl(EndpointControllerTest.DEFAULT_URL.getBytes());
    endpoint.setResponseBody("body".getBytes());
    return endpoint;
  }

  private List<Endpoint> generateEndpoints() throws Exception {
    List<Endpoint> endpoints = new ArrayList<>();
    Endpoint endpoint = this.generateEndpoint();
    endpoint.setPathVariable("data".getBytes());
    endpoint.setRequestParam("data".getBytes());
    endpoints.add(endpoint);
    endpoints.add(this.generateEndpoint());
    return endpoints;
  }

  private CreateEndpointRequest generateCreateEndpointRequest() throws Exception {
    CreateEndpointRequest request = new CreateEndpointRequest();
    request.setModuleCode(EndpointControllerTest.DEFAULT_MODULE_CODE);
    request.setUrl(EndpointControllerTest.DEFAULT_URL);
    request.setRequestMethod(RequestMethod.GET);
    request.getPathVariables().add("id");
    request.getRequestParams().add("requestId");
    request.setResponseBody("body");
    return request;
  }

  private HttpServletRequest generateHttpServletRequest() throws Exception {
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setRequestURI(EndpointControllerTest.DEFAULT_REQUEST_URI);
    return request;
  }

  private HttpServletResponse generateHttpServletResponse() throws Exception {
    MockHttpServletResponse response = new MockHttpServletResponse();
    return response;
  }

  @Before
  public void initializeTest() throws Exception {
    MockitoAnnotations.initMocks(this);
    List<Endpoint> endpoints = this.generateEndpoints();
    Mockito.when(this.endpointService.findByModuleCode(Mockito.anyString())).thenReturn(endpoints);
    Mockito.when(this.objectMapper.readValue(Mockito.anyString(), Mockito.any(TypeReference.class)))
        .thenReturn(new HashSet<>());
    Mockito.when(this.objectMapper.writeValueAsString(Mockito.anyObject())).thenReturn("data");
    Mockito.doNothing().when(this.endpointService).create(Mockito.any(Endpoint.class));
    Mockito.doNothing().when(this.endpointService).enable(Mockito.anyString());
    Mockito.doNothing().when(this.endpointService).disable(Mockito.anyString());
    Mockito.doNothing().when(this.endpointService).delete(Mockito.anyString());
    Mockito.when(this.endpointService.mockup(Mockito.any(HttpMethod.class), Mockito.any(
        HttpServletRequest.class), Mockito.any(HttpServletResponse.class), Mockito.anyString(),
        Mockito.anyString())).thenReturn(null);
  }

  @After
  public void finalizeTest() throws Exception {
    Mockito.verifyNoMoreInteractions(this.objectMapper);
    Mockito.verifyNoMoreInteractions(this.endpointService);
  }

  @Test
  public void filterByModuleCodeTest() throws Exception {
    this.endpointController.filterByModuleCode(EndpointControllerTest.DEFAULT_REQUEST_ID,
        EndpointControllerTest.DEFAULT_MODULE_CODE);
    Mockito.verify(this.endpointService).findByModuleCode(Mockito.anyString());
    Mockito.verify(this.objectMapper, EndpointControllerTest.CALLED_TWICE)
        .readValue(Mockito.anyString(), Mockito.any(TypeReference.class));
  }

  @Test
  public void createTest() throws Exception {
    this.endpointController
        .create(EndpointControllerTest.DEFAULT_REQUEST_ID, this.generateCreateEndpointRequest());
    Mockito.verify(this.endpointService).create(Mockito.any(Endpoint.class));
    Mockito.verify(this.objectMapper, EndpointControllerTest.CALLED_TWICE)
        .writeValueAsString(Mockito.anyObject());
  }

  @Test
  public void createWithSimpleRequest_1_Test() throws Exception {
    CreateEndpointRequest request = this.generateCreateEndpointRequest();
    request.setPathVariables(null);
    request.setRequestParams(null);
    request.setResponseBody(null);
    this.endpointController
        .create(EndpointControllerTest.DEFAULT_REQUEST_ID, request);
    Mockito.verify(this.endpointService).create(Mockito.any(Endpoint.class));
    Mockito.verify(this.objectMapper, EndpointControllerTest.NEVER_CALLED)
        .writeValueAsString(Mockito.anyObject());
  }

  @Test
  public void createWithSimpleRequest_2_Test() throws Exception {
    CreateEndpointRequest request = this.generateCreateEndpointRequest();
    request.getPathVariables().clear();
    request.getRequestParams().clear();
    request.setResponseBody(null);
    this.endpointController
        .create(EndpointControllerTest.DEFAULT_REQUEST_ID, request);
    Mockito.verify(this.endpointService).create(Mockito.any(Endpoint.class));
    Mockito.verify(this.objectMapper, EndpointControllerTest.NEVER_CALLED)
        .writeValueAsString(Mockito.anyObject());
  }

  @Test(expected = Exception.class)
  public void createWithModuleCodeExceptionTest() throws Exception {
    CreateEndpointRequest request = this.generateCreateEndpointRequest();
    request.setModuleCode(null);
    try {
      this.endpointController
          .create(EndpointControllerTest.DEFAULT_REQUEST_ID, request);
    } catch (Exception e) {
      Assert.assertEquals(EndpointControllerErrorMessage.MODULE_CODE_MUST_NOT_BE_BLANK,
          e.getMessage());
      Mockito.verify(this.endpointService, EndpointControllerTest.NEVER_CALLED)
          .create(Mockito.any(Endpoint.class));
      Mockito.verify(this.objectMapper, EndpointControllerTest.NEVER_CALLED)
          .writeValueAsString(Mockito.anyObject());
      throw e;
    }
  }

  @Test(expected = Exception.class)
  public void createWithUrlExceptionTest() throws Exception {
    CreateEndpointRequest request = this.generateCreateEndpointRequest();
    request.setUrl(null);
    try {
      this.endpointController
          .create(EndpointControllerTest.DEFAULT_REQUEST_ID, request);
    } catch (Exception e) {
      Assert.assertEquals(EndpointControllerErrorMessage.URL_MUST_NOT_BE_BLANK, e.getMessage());
      Mockito.verify(this.endpointService, EndpointControllerTest.NEVER_CALLED)
          .create(Mockito.any(Endpoint.class));
      Mockito.verify(this.objectMapper, EndpointControllerTest.NEVER_CALLED)
          .writeValueAsString(Mockito.anyObject());
      throw e;
    }
  }

  @Test(expected = Exception.class)
  public void createWithRequestMethodExceptionTest() throws Exception {
    CreateEndpointRequest request = this.generateCreateEndpointRequest();
    request.setRequestMethod(null);
    try {
      this.endpointController
          .create(EndpointControllerTest.DEFAULT_REQUEST_ID, request);
    } catch (Exception e) {
      Assert.assertEquals(EndpointControllerErrorMessage.REQUEST_METHOD_MUST_NOT_BE_BLANK,
          e.getMessage());
      Mockito.verify(this.endpointService, EndpointControllerTest.NEVER_CALLED)
          .create(Mockito.any(Endpoint.class));
      Mockito.verify(this.objectMapper, EndpointControllerTest.NEVER_CALLED)
          .writeValueAsString(Mockito.anyObject());
      throw e;
    }
  }

  @Test
  public void enableTest() throws Exception {
    this.endpointController.enable(EndpointControllerTest.DEFAULT_REQUEST_ID, null);
    Mockito.verify(this.endpointService).enable(Mockito.anyString());
  }

  @Test
  public void disableTest() throws Exception {
    this.endpointController.disable(EndpointControllerTest.DEFAULT_REQUEST_ID, null);
    Mockito.verify(this.endpointService).disable(Mockito.anyString());
  }

  @Test
  public void deleteTest() throws Exception {
    this.endpointController.delete(EndpointControllerTest.DEFAULT_REQUEST_ID, null);
    Mockito.verify(this.endpointService).delete(Mockito.anyString());
  }

  @Test
  public void mockupTest() throws Exception {
    this.endpointController.mockup(HttpMethod.GET, this.generateHttpServletRequest(),
        this.generateHttpServletResponse(), null);
    Mockito.verify(this.endpointService).mockup(Mockito.any(HttpMethod.class), Mockito.any(
        HttpServletRequest.class), Mockito.any(HttpServletResponse.class), Mockito.anyString(),
        Mockito.anyString());
  }

}
