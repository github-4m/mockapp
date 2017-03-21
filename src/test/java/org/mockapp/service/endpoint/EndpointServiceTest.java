package org.mockapp.service.endpoint;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockapp.entity.Endpoint;
import org.mockapp.entity.Module;
import org.mockapp.model.wiremock.Wiremock;
import org.mockapp.outbound.wiremock.WiremockOutbound;
import org.mockapp.repository.endpoint.EndpointRepository;
import org.mockapp.repository.module.ModuleRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.verification.VerificationMode;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by fathan.mustaqim on 3/18/2017.
 */
public class EndpointServiceTest {

  private static final String DEFAULT_RESOURCE_FILE = "mockup/default.json";
  private static final String DEFAULT_CODE = UUID.randomUUID().toString();
  private static final String DEFAULT_MODULE_CODE = UUID.randomUUID().toString();
  private static final String DEFAULT_CONTEXT_PATH = "mockapp";
  private static final String DEFAULT_PATH = "/test/{id}";
  private static final String DEFAULT_REQUEST_ID = UUID.randomUUID().toString();
  private static final VerificationMode CALLED_TWICE = Mockito.times(2);
  private static final VerificationMode NEVER_CALLED = Mockito.times(0);

  @Mock
  private ObjectMapper objectMapper;

  @Mock
  private ModuleRepository moduleRepository;

  @Mock
  private EndpointRepository endpointRepository;

  @Mock
  private WiremockOutbound wiremockOutbound;

  @InjectMocks
  private EndpointServiceBean endpointServiceBean;

  private void generateWiremockDirectory() throws Exception {
    ClassLoader classLoader = this.getClass().getClassLoader();
    File file = new File(
        classLoader.getResource(EndpointServiceTest.DEFAULT_RESOURCE_FILE).toURI());
    File directory = file.getParentFile();
    ReflectionTestUtils
        .setField(this.endpointServiceBean, "wiremockDirectory", directory.getAbsolutePath());
  }

  private void generateEndpointFile() throws Exception {
    ClassLoader classLoader = this.getClass().getClassLoader();
    File file = new File(
        classLoader.getResource(EndpointServiceTest.DEFAULT_RESOURCE_FILE).toURI());
    File directory = file.getParentFile();
    File moduleDirectory = new File(directory,
        "mappings/" + EndpointServiceTest.DEFAULT_MODULE_CODE);
    if (!moduleDirectory.exists()) {
      moduleDirectory.mkdirs();
    }
    File endpointFile = new File(moduleDirectory, EndpointServiceTest.DEFAULT_CODE + ".json");
    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(endpointFile));
    bos.write("data".getBytes());
    bos.close();
  }

  private Set<String> generatePathVariables() throws Exception {
    Set<String> pathVariables = new HashSet<>();
    pathVariables.add("id");
    return pathVariables;
  }

  private Set<String> generateRequestParams() throws Exception {
    Set<String> requestParams = new HashSet<>();
    requestParams.add("requestId");
    return requestParams;
  }

  private Module generateModule() throws Exception {
    Module module = new Module();
    module.setCode(EndpointServiceTest.DEFAULT_MODULE_CODE);
    module.setEnable(true);
    module.setContextPath(EndpointServiceTest.DEFAULT_CONTEXT_PATH);
    return module;
  }

  private Endpoint generateEndpoint() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper(new JsonFactory());
    Endpoint endpoint = new Endpoint();
    endpoint.setModuleCode(EndpointServiceTest.DEFAULT_MODULE_CODE);
    endpoint.setCode(EndpointServiceTest.DEFAULT_CODE);
    endpoint.setEnable(true);
    endpoint.setRequestMethod(RequestMethod.GET);
    endpoint.setUrl("/test/id".getBytes());
    endpoint
        .setPathVariable(objectMapper.writeValueAsString(this.generatePathVariables()).getBytes());
    endpoint
        .setRequestParam(objectMapper.writeValueAsString(this.generateRequestParams()).getBytes());
    endpoint.setResponseBody("body".getBytes());
    return endpoint;
  }

  private List<Endpoint> generateEndpoints() throws Exception {
    List<Endpoint> endpoints = new ArrayList<>();
    endpoints.add(this.generateEndpoint());
    return endpoints;
  }

  private Map<String, String> generateResponseBody() throws Exception {
    Map<String, String> responseBody = new HashMap<>();
    responseBody.put("endpointCode", EndpointServiceTest.DEFAULT_CODE);
    responseBody.put("body", "body");
    return responseBody;
  }

  private HttpServletRequest generateHttpServletRequest() throws Exception {
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addParameter("requestId", EndpointServiceTest.DEFAULT_REQUEST_ID);
    return request;
  }

  private HttpServletResponse generateHttpServletResponse() throws Exception {
    MockHttpServletResponse response = new MockHttpServletResponse();
    return response;
  }

  @Before
  public void initializeTest() throws Exception {
    MockitoAnnotations.initMocks(this);
    this.generateWiremockDirectory();
    Module module = this.generateModule();
    Endpoint endpoint = this.generateEndpoint();
    List<Endpoint> endpoints = this.generateEndpoints();
    ObjectMapper objectMapper = new ObjectMapper(new JsonFactory());
    String responseBody = objectMapper.writeValueAsString(this.generateResponseBody());
    Mockito.when(this.moduleRepository.findByCodeAndMarkForDeleteFalse(Mockito.anyString()))
        .thenReturn(module);
    Mockito.when(this.endpointRepository.save(Mockito.any(Endpoint.class))).thenReturn(null);
    Mockito.doNothing().when(this.wiremockOutbound).reset();
    Mockito.when(this.endpointRepository.findByModuleCodeAndMarkForDeleteFalse(Mockito.anyString()))
        .thenReturn(endpoints);
    Mockito.when(this.endpointRepository.findByCodeAndMarkForDeleteFalse(Mockito.anyString()))
        .thenReturn(endpoint);
    Mockito.doNothing().when(this.endpointRepository).enableByCode(Mockito.anyString());
    Mockito.doNothing().when(this.endpointRepository).disableByCode(Mockito.anyString());
    Mockito.doNothing().when(this.endpointRepository).deleteByCode(Mockito.anyString());
    Mockito.when(this.wiremockOutbound.mockup(Mockito.any(HttpMethod.class), Mockito.any(
        HttpServletRequest.class), Mockito.any(HttpServletResponse.class), Mockito.anyString(),
        Mockito.anyString())).thenReturn(responseBody);
    Mockito.when(
        this.objectMapper.readValue(Mockito.eq(responseBody), Mockito.any(TypeReference.class)))
        .thenReturn(this.generateResponseBody());
    Mockito.when(this.objectMapper
        .readValue(Mockito.eq(objectMapper.writeValueAsString(this.generatePathVariables())),
            Mockito.any(TypeReference.class))).thenReturn(this.generatePathVariables());
    Mockito.when(
        this.objectMapper.readValue(
            Mockito.eq(objectMapper.writeValueAsString(this.generateRequestParams())),
            Mockito.any(TypeReference.class))).thenReturn(this.generateRequestParams());
    Mockito.when(this.objectMapper.writeValueAsString(Mockito.anyMap())).thenReturn(null);
    Mockito.when(this.objectMapper.writeValueAsString(Mockito.any(Wiremock.class)))
        .thenReturn("data");
  }

  @After
  public void finalizeTest() throws Exception {
    Mockito.verifyNoMoreInteractions(this.objectMapper);
    Mockito.verifyNoMoreInteractions(this.moduleRepository);
    Mockito.verifyNoMoreInteractions(this.endpointRepository);
    Mockito.verifyNoMoreInteractions(this.wiremockOutbound);
  }

  @Test
  public void createTest() throws Exception {
    this.endpointServiceBean.create(this.generateEndpoint());
    Mockito.verify(this.moduleRepository).findByCodeAndMarkForDeleteFalse(Mockito.anyString());
    Mockito.verify(this.endpointRepository).save(Mockito.any(Endpoint.class));
    Mockito.verify(this.wiremockOutbound).reset();
    Mockito.verify(this.objectMapper, EndpointServiceTest.CALLED_TWICE)
        .readValue(Mockito.anyString(), Mockito.any(TypeReference.class));
    Mockito.verify(this.objectMapper, EndpointServiceTest.CALLED_TWICE)
        .writeValueAsString(Mockito.anyObject());
  }

  @Test
  public void createWithSimpleRequestTest() throws Exception {
    Endpoint endpoint = this.generateEndpoint();
    endpoint.setPathVariable(null);
    endpoint.setRequestParam(null);
    endpoint.setResponseBody(null);
    this.endpointServiceBean.create(endpoint);
    Mockito.verify(this.moduleRepository).findByCodeAndMarkForDeleteFalse(Mockito.anyString());
    Mockito.verify(this.endpointRepository).save(Mockito.any(Endpoint.class));
    Mockito.verify(this.wiremockOutbound).reset();
    Mockito.verify(this.objectMapper, EndpointServiceTest.NEVER_CALLED)
        .readValue(Mockito.anyString(), Mockito.any(TypeReference.class));
    Mockito.verify(this.objectMapper, EndpointServiceTest.CALLED_TWICE)
        .writeValueAsString(Mockito.anyObject());
  }

  @Test(expected = Exception.class)
  public void createWithExceptionTest() throws Exception {
    Mockito.when(this.moduleRepository.findByCodeAndMarkForDeleteFalse(Mockito.anyString()))
        .thenReturn(null);
    try {
      this.endpointServiceBean.create(this.generateEndpoint());
    } catch (Exception e) {
      Mockito.verify(this.moduleRepository).findByCodeAndMarkForDeleteFalse(Mockito.anyString());
      Mockito.verify(this.endpointRepository, EndpointServiceTest.NEVER_CALLED)
          .save(Mockito.any(Endpoint.class));
      Mockito.verify(this.wiremockOutbound, EndpointServiceTest.NEVER_CALLED).reset();
      Mockito.verify(this.objectMapper, EndpointServiceTest.NEVER_CALLED)
          .readValue(Mockito.anyString(), Mockito.any(TypeReference.class));
      Mockito.verify(this.objectMapper, EndpointServiceTest.NEVER_CALLED)
          .writeValueAsString(Mockito.anyObject());
      throw e;
    }
  }

  @Test
  public void findByModuleCodeTest() throws Exception {
    this.endpointServiceBean.findByModuleCode(null);
    Mockito.verify(this.endpointRepository)
        .findByModuleCodeAndMarkForDeleteFalse(Mockito.anyString());
  }

  @Test
  public void enableTest() throws Exception {
    this.endpointServiceBean.enable(null);
    Mockito.verify(this.endpointRepository).findByCodeAndMarkForDeleteFalse(Mockito.anyString());
    Mockito.verify(this.endpointRepository).enableByCode(Mockito.anyString());
  }

  @Test(expected = Exception.class)
  public void enableWithExceptionTest() throws Exception {
    Mockito.when(this.endpointRepository.findByCodeAndMarkForDeleteFalse(Mockito.anyString()))
        .thenReturn(null);
    try {
      this.endpointServiceBean.enable(null);
    } catch (Exception e) {
      Mockito.verify(this.endpointRepository).findByCodeAndMarkForDeleteFalse(Mockito.anyString());
      Mockito.verify(this.endpointRepository, EndpointServiceTest.NEVER_CALLED)
          .enableByCode(Mockito.anyString());
      throw e;
    }
  }

  @Test
  public void disableTest() throws Exception {
    this.endpointServiceBean.disable(null);
    Mockito.verify(this.endpointRepository).findByCodeAndMarkForDeleteFalse(Mockito.anyString());
    Mockito.verify(this.endpointRepository).disableByCode(Mockito.anyString());
  }

  @Test(expected = Exception.class)
  public void disableWithExceptionTest() throws Exception {
    Mockito.when(this.endpointRepository.findByCodeAndMarkForDeleteFalse(Mockito.anyString()))
        .thenReturn(null);
    try {
      this.endpointServiceBean.disable(null);
    } catch (Exception e) {
      Mockito.verify(this.endpointRepository).findByCodeAndMarkForDeleteFalse(Mockito.anyString());
      Mockito.verify(this.endpointRepository, EndpointServiceTest.NEVER_CALLED)
          .disableByCode(Mockito.anyString());
      throw e;
    }
  }

  @Test
  public void deleteTest() throws Exception {
    this.generateEndpointFile();
    this.endpointServiceBean.delete(null);
    Mockito.verify(this.endpointRepository).findByCodeAndMarkForDeleteFalse(Mockito.anyString());
    Mockito.verify(this.endpointRepository).deleteByCode(Mockito.anyString());
    Mockito.verify(this.wiremockOutbound).reset();
  }

  @Test
  public void deleteWithoutDeleteEndpointFileTest() throws Exception {
    this.endpointServiceBean.delete(null);
    Mockito.verify(this.endpointRepository).findByCodeAndMarkForDeleteFalse(Mockito.anyString());
    Mockito.verify(this.endpointRepository).deleteByCode(Mockito.anyString());
    Mockito.verify(this.wiremockOutbound).reset();
  }

  @Test(expected = Exception.class)
  public void deleteWithExceptionTest() throws Exception {
    Mockito.when(this.endpointRepository.findByCodeAndMarkForDeleteFalse(Mockito.anyString()))
        .thenReturn(null);
    try {
      this.endpointServiceBean.delete(null);
    } catch (Exception e) {
      Mockito.verify(this.endpointRepository).findByCodeAndMarkForDeleteFalse(Mockito.anyString());
      Mockito.verify(this.endpointRepository, EndpointServiceTest.NEVER_CALLED)
          .deleteByCode(Mockito.anyString());
      Mockito.verify(this.wiremockOutbound, EndpointServiceTest.NEVER_CALLED).reset();
      throw e;
    }
  }

  @Test
  public void mockupTest() throws Exception {
    this.endpointServiceBean.mockup(HttpMethod.GET, this.generateHttpServletRequest(),
        this.generateHttpServletResponse(), EndpointServiceTest.DEFAULT_PATH, null);
    Mockito.verify(this.wiremockOutbound).mockup(Mockito.any(HttpMethod.class), Mockito.any(
        HttpServletRequest.class), Mockito.any(HttpServletResponse.class), Mockito.anyString(),
        Mockito.anyString());
    Mockito.verify(this.endpointRepository).findByCodeAndMarkForDeleteFalse(Mockito.anyString());
    Mockito.verify(this.moduleRepository).findByCodeAndMarkForDeleteFalse(Mockito.anyString());
    Mockito.verify(this.objectMapper, EndpointServiceTest.CALLED_TWICE)
        .readValue(Mockito.anyString(), Mockito.any(TypeReference.class));
  }

  @Test
  public void mockupWithSimpleRequestTest() throws Exception {
    Endpoint endpoint = this.generateEndpoint();
    endpoint.setUrl(null);
    endpoint.setPathVariable(null);
    endpoint.setRequestParam(null);
    Mockito.when(this.endpointRepository.findByCodeAndMarkForDeleteFalse(Mockito.anyString()))
        .thenReturn(endpoint);
    this.endpointServiceBean.mockup(HttpMethod.GET, this.generateHttpServletRequest(),
        this.generateHttpServletResponse(), null, null);
    Mockito.verify(this.wiremockOutbound).mockup(Mockito.any(HttpMethod.class), Mockito.any(
        HttpServletRequest.class), Mockito.any(HttpServletResponse.class), Mockito.anyString(),
        Mockito.anyString());
    Mockito.verify(this.endpointRepository).findByCodeAndMarkForDeleteFalse(Mockito.anyString());
    Mockito.verify(this.moduleRepository).findByCodeAndMarkForDeleteFalse(Mockito.anyString());
    Mockito.verify(this.objectMapper)
        .readValue(Mockito.anyString(), Mockito.any(TypeReference.class));
  }

  @Test(expected = Exception.class)
  public void mockupWithEndpointExceptionTest() throws Exception {
    Mockito.when(this.endpointRepository.findByCodeAndMarkForDeleteFalse(Mockito.anyString()))
        .thenReturn(null);
    try {
      this.endpointServiceBean.mockup(HttpMethod.GET, this.generateHttpServletRequest(),
          this.generateHttpServletResponse(), null, null);
    } catch (Exception e) {
      Mockito.verify(this.wiremockOutbound).mockup(Mockito.any(HttpMethod.class), Mockito.any(
          HttpServletRequest.class), Mockito.any(HttpServletResponse.class), Mockito.anyString(),
          Mockito.anyString());
      Mockito.verify(this.objectMapper)
          .readValue(Mockito.anyString(), Mockito.any(TypeReference.class));
      Mockito.verify(this.endpointRepository).findByCodeAndMarkForDeleteFalse(Mockito.anyString());
      Mockito.verify(this.moduleRepository, EndpointServiceTest.NEVER_CALLED)
          .findByCodeAndMarkForDeleteFalse(Mockito.anyString());
      throw e;
    }
  }

  @Test(expected = Exception.class)
  public void mockupWithModuleExceptionTest() throws Exception {
    Mockito.when(this.moduleRepository.findByCodeAndMarkForDeleteFalse(Mockito.anyString()))
        .thenReturn(null);
    try {
      this.endpointServiceBean.mockup(HttpMethod.GET, this.generateHttpServletRequest(),
          this.generateHttpServletResponse(), null, null);
    } catch (Exception e) {
      Mockito.verify(this.wiremockOutbound).mockup(Mockito.any(HttpMethod.class), Mockito.any(
          HttpServletRequest.class), Mockito.any(HttpServletResponse.class), Mockito.anyString(),
          Mockito.anyString());
      Mockito.verify(this.objectMapper)
          .readValue(Mockito.anyString(), Mockito.any(TypeReference.class));
      Mockito.verify(this.endpointRepository).findByCodeAndMarkForDeleteFalse(Mockito.anyString());
      Mockito.verify(this.moduleRepository).findByCodeAndMarkForDeleteFalse(Mockito.anyString());
      throw e;
    }
  }

  @Test(expected = Exception.class)
  public void mockupWithDisabledModuleExceptionTest() throws Exception {
    Module module = this.generateModule();
    module.setEnable(false);
    Mockito.when(this.moduleRepository.findByCodeAndMarkForDeleteFalse(Mockito.anyString()))
        .thenReturn(module);
    try {
      this.endpointServiceBean.mockup(HttpMethod.GET, this.generateHttpServletRequest(),
          this.generateHttpServletResponse(), null, null);
    } catch (Exception e) {
      Mockito.verify(this.wiremockOutbound).mockup(Mockito.any(HttpMethod.class), Mockito.any(
          HttpServletRequest.class), Mockito.any(HttpServletResponse.class), Mockito.anyString(),
          Mockito.anyString());
      Mockito.verify(this.objectMapper)
          .readValue(Mockito.anyString(), Mockito.any(TypeReference.class));
      Mockito.verify(this.endpointRepository).findByCodeAndMarkForDeleteFalse(Mockito.anyString());
      Mockito.verify(this.moduleRepository).findByCodeAndMarkForDeleteFalse(Mockito.anyString());
      throw e;
    }
  }

  @Test(expected = Exception.class)
  public void mockupWithDisabledEndpointExceptionTest() throws Exception {
    Endpoint endpoint = this.generateEndpoint();
    endpoint.setEnable(false);
    Mockito.when(this.endpointRepository.findByCodeAndMarkForDeleteFalse(Mockito.anyString()))
        .thenReturn(endpoint);
    try {
      this.endpointServiceBean.mockup(HttpMethod.GET, this.generateHttpServletRequest(),
          this.generateHttpServletResponse(), null, null);
    } catch (Exception e) {
      Mockito.verify(this.wiremockOutbound).mockup(Mockito.any(HttpMethod.class), Mockito.any(
          HttpServletRequest.class), Mockito.any(HttpServletResponse.class), Mockito.anyString(),
          Mockito.anyString());
      Mockito.verify(this.objectMapper)
          .readValue(Mockito.anyString(), Mockito.any(TypeReference.class));
      Mockito.verify(this.endpointRepository).findByCodeAndMarkForDeleteFalse(Mockito.anyString());
      Mockito.verify(this.moduleRepository).findByCodeAndMarkForDeleteFalse(Mockito.anyString());
      throw e;
    }
  }

}
