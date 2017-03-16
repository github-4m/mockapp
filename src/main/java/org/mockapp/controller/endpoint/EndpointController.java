package org.mockapp.controller.endpoint;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.mockapp.dto.BaseResponse;
import org.mockapp.dto.ListBaseResponse;
import org.mockapp.dto.endpoint.CreateEndpointRequest;
import org.mockapp.dto.endpoint.EndpointResponse;
import org.mockapp.entity.Endpoint;
import org.mockapp.service.endpoint.EndpointService;
import org.mockapp.util.Precondition;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by fathan.mustaqiim on 3/15/2017.
 */
@RestController
@RequestMapping(value = EndpointControllerPath.BASE_PATH)
public class EndpointController {

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private EndpointService endpointService;

  private Endpoint generateEndpoint(CreateEndpointRequest request) throws Exception {
    Endpoint endpoint = new Endpoint();
    endpoint.setModuleCode(request.getModuleCode());
    endpoint.setUrl(request.getUrl().getBytes());
    endpoint.setRequestMethod(request.getRequestMethod());
    if (request.getRequestParams() != null && !request.getRequestParams().isEmpty()) {
      endpoint.setRequestParam(
          this.objectMapper.writeValueAsString(request.getRequestParams()).getBytes());
    }
    if (request.getPathVariables() != null && !request.getPathVariables().isEmpty()) {
      endpoint.setPathVariable(
          this.objectMapper.writeValueAsString(request.getPathVariables()).getBytes());
    }
    if (!StringUtils.isEmpty(request.getResponseBody())) {
      endpoint.setResponseBody(request.getResponseBody().getBytes());
    }
    return endpoint;
  }

  private EndpointResponse generateEndpointResponse(Endpoint endpoint) throws Exception {
    EndpointResponse endpointResponse = new EndpointResponse();
    BeanUtils.copyProperties(endpoint, endpointResponse,
        new String[]{"url", "requestParam", "pathVariable", "responseBody"});
    Set<String> pathVariables = this.objectMapper
        .readValue(String.valueOf(endpoint.getPathVariable()), new TypeReference<HashSet>() {
        });
    Set<String> requestParams = this.objectMapper.readValue(
        String.valueOf(endpoint.getRequestParam()), new TypeReference<HashSet>() {
        });
    endpointResponse.setUrl(String.valueOf(endpoint.getUrl()));
    endpointResponse.setPathVariables(pathVariables);
    endpointResponse.setRequestParams(requestParams);
    endpointResponse.setResponseBody(String.valueOf(endpoint.getResponseBody()));
    return endpointResponse;
  }

  private List<EndpointResponse> generateEndpointResponses(List<Endpoint> endpoints)
      throws Exception {
    List<EndpointResponse> endpointResponses = new ArrayList<>();
    for (Endpoint endpoint : endpoints) {
      endpointResponses.add(this.generateEndpointResponse(endpoint));
    }
    return endpointResponses;
  }

  @RequestMapping(value = EndpointControllerPath.FILTER_MODULE_CODE, method = RequestMethod.GET)
  public ListBaseResponse<EndpointResponse> filterByModuleCode(@RequestParam String requestId,
      @RequestParam String moduleCode) throws Exception {
    List<Endpoint> endpoints = this.endpointService.findByModuleCode(moduleCode);
    return new ListBaseResponse<>(null, null, true, requestId,
        this.generateEndpointResponses(endpoints));
  }

  @RequestMapping(value = EndpointControllerPath.CREATE, method = RequestMethod.POST, consumes = {
      MediaType.APPLICATION_JSON_VALUE})
  public BaseResponse create(@RequestParam String requestId,
      @RequestBody CreateEndpointRequest request) throws Exception {
    Precondition.checkArgument(!StringUtils.isEmpty(request.getModuleCode()),
        EndpointControllerErrorMessage.MODULE_CODE_MUST_NOT_BE_BLANK);
    Precondition.checkArgument(!StringUtils.isEmpty(request.getUrl()),
        EndpointControllerErrorMessage.URL_MUST_NOT_BE_BLANK);
    Precondition.checkArgument(request.getRequestMethod() != null,
        EndpointControllerErrorMessage.REQUEST_METHOD_MUST_NOT_BE_BLANK);
    Endpoint endpoint = this.generateEndpoint(request);
    this.endpointService.create(endpoint);
    return new BaseResponse(null, null, true, requestId);
  }

  @RequestMapping(value = EndpointControllerPath.ENABLE, method = RequestMethod.GET)
  public BaseResponse enable(@RequestParam String requestId, @RequestParam String code)
      throws Exception {
    this.endpointService.enable(code);
    return new BaseResponse(null, null, true, requestId);
  }

  @RequestMapping(value = EndpointControllerPath.DISABLE, method = RequestMethod.GET)
  public BaseResponse disable(@RequestParam String requestId, @RequestParam String code)
      throws Exception {
    this.endpointService.disable(code);
    return new BaseResponse(null, null, true, requestId);
  }

  @RequestMapping(value = EndpointControllerPath.DELETE, method = RequestMethod.GET)
  public BaseResponse delete(@RequestParam String requestId, @RequestParam String code)
      throws Exception {
    this.endpointService.delete(code);
    return new BaseResponse(null, null, true, requestId);
  }

  @RequestMapping(value = EndpointControllerPath.MOCKUP)
  public String mockup(HttpMethod method, HttpServletRequest request, HttpServletResponse response,
      @RequestBody(required = false) String requestBody) throws Exception {
    String path = request.getRequestURI().replace(EndpointControllerPath.BASE_PATH + "/mockup", "");
    return this.endpointService.mockup(method, request, response, path, requestBody);
  }

}
