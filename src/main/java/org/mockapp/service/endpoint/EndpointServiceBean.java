package org.mockapp.service.endpoint;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.mockapp.entity.Endpoint;
import org.mockapp.entity.Module;
import org.mockapp.model.wiremock.Wiremock;
import org.mockapp.model.wiremock.WiremockRequest;
import org.mockapp.model.wiremock.WiremockResponse;
import org.mockapp.model.wiremock.WiremockResponseBody;
import org.mockapp.outbound.wiremock.WiremockOutbound;
import org.mockapp.repository.endpoint.EndpointRepository;
import org.mockapp.repository.module.ModuleRepository;
import org.mockapp.util.Generator;
import org.mockapp.util.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by fathan.mustaqiim on 3/15/2017.
 */
@Service
@Transactional(readOnly = true)
public class EndpointServiceBean implements EndpointService {

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private ModuleRepository moduleRepository;

  @Autowired
  private EndpointRepository endpointRepository;

  @Autowired
  private WiremockOutbound wiremockOutbound;

  @Value("${wiremock.directory}")
  private String wiremockDirectory;

  private void createEndpointFile(Module module, Endpoint endpoint) throws Exception {
    String url = module.getContextPath() + "/" + String.valueOf(endpoint.getUrl());
    if (endpoint.getPathVariable() != null) {
      Set<String> pathVariables = this.objectMapper
          .readValue(String.valueOf(endpoint.getPathVariable()), new TypeReference<HashSet>() {
          });
      for (int i = 0; i < pathVariables.size(); i++) {
        url += "/" + Pattern.ALPHANUMERIC_UNDERSCORE_DASH;
      }
    }
    if (endpoint.getRequestParam() != null) {
      Set<String> requestParams = this.objectMapper
          .readValue(String.valueOf(endpoint.getRequestParam()), new TypeReference<HashSet>() {
          });
      if (!requestParams.isEmpty()) {
        url += "\\?" + Pattern.ANY;
      }
    }
    WiremockRequest request = new WiremockRequest();
    request.setMethod(endpoint.getRequestMethod().name());
    request.setUrlPattern(url);
    WiremockResponseBody body = new WiremockResponseBody();
    body.setEndpointCode(endpoint.getCode());
    if (endpoint.getResponseBody() != null) {
      body.setBody(String.valueOf(endpoint.getResponseBody()));
    }
    WiremockResponse response = new WiremockResponse();
    response.getHeaders().put(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(200);
    response.setBody(body);
    Wiremock wiremock = new Wiremock();
    wiremock.setRequest(request);
    wiremock.setResponse(response);
    byte[] data = this.objectMapper.writeValueAsString(wiremock).getBytes();
    File file = new File(
        this.wiremockDirectory + "/mappings/" + endpoint.getModuleCode() + "/" + endpoint.getCode()
            + ".json");
    File directory = file.getParentFile();
    if (!directory.exists()) {
      directory.mkdirs();
    }
    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
    bos.write(data);
    bos.close();
  }

  private void deleteEndpointFile(Endpoint endpoint) throws Exception {
    File file = new File(
        this.wiremockDirectory + "/mappings/" + endpoint.getModuleCode() + "/" + endpoint.getCode()
            + ".json");
    if (file.exists()) {
      file.delete();
    }
  }

  @Override
  @Transactional(readOnly = false, rollbackFor = Exception.class)
  public void create(Endpoint endpoint) throws Exception {
    Module module = this.moduleRepository.findByCodeAndMarkForDeleteFalse(endpoint.getModuleCode());
    if (module == null) {
      throw new Exception("Invalid module code");
    }
    endpoint.setCode(Generator.generateCode());
    endpoint.setModule(module);
    this.endpointRepository.save(endpoint);
    this.createEndpointFile(module, endpoint);
    this.wiremockOutbound.reset();
  }

  @Override
  public List<Endpoint> findByModuleCode(String moduleCode) throws Exception {
    return this.endpointRepository.findByModuleCodeAndMarkForDeleteFalse(moduleCode);
  }

  @Override
  @Transactional(readOnly = false, rollbackFor = Exception.class)
  public void enable(String code) throws Exception {
    Endpoint endpoint = this.endpointRepository.findByCodeAndMarkForDeleteFalse(code);
    if (endpoint == null) {
      throw new Exception("Invalid code");
    }
    this.endpointRepository.enableByCode(code);
  }

  @Override
  @Transactional(readOnly = false, rollbackFor = Exception.class)
  public void disable(String code) throws Exception {
    Endpoint endpoint = this.endpointRepository.findByCodeAndMarkForDeleteFalse(code);
    if (endpoint == null) {
      throw new Exception("Invalid code");
    }
    this.endpointRepository.disableByCode(code);
  }

  @Override
  @Transactional(readOnly = false, rollbackFor = Exception.class)
  public void delete(String code) throws Exception {
    Endpoint endpoint = this.endpointRepository.findByCodeAndMarkForDeleteFalse(code);
    if (endpoint == null) {
      throw new Exception("Invalid code");
    }
    this.endpointRepository.deleteByCode(code);
    this.deleteEndpointFile(endpoint);
    this.wiremockOutbound.reset();
  }

  @Override
  public String mockup(HttpMethod method, HttpServletRequest request, HttpServletResponse response,
      String path, String requestBody) throws Exception {
    String value = this.wiremockOutbound
        .mockup(method, request, response, path, requestBody);
    WiremockResponseBody body = this.objectMapper.readValue(value, WiremockResponseBody.class);
    Endpoint endpoint = this.endpointRepository
        .findByCodeAndMarkForDeleteFalse(body.getEndpointCode());
    if (endpoint == null) {
      throw new Exception("Unauthorized api access");
    }
    Module module = this.moduleRepository.findByCodeAndMarkForDeleteFalse(endpoint.getModuleCode());
    if (module == null) {
      throw new Exception("Unauthorized api access");
    }
    if (!module.isEnable() || !endpoint.isEnable()) {
      throw new Exception("Unauthorized api access");
    }
    return body.getBody();
  }
}
