package org.mockapp.outbound;

import java.net.URI;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Created by 4than.mustaqiim on 3/9/2017.
 */
public class HttpInvoker {

  @Autowired
  private RestTemplate restTemplate;

  private URI generateUri(List<NameValuePair> parameters, String scheme, String host, Integer port,
      String path) throws
      Exception {
    if (parameters == null) {
      parameters = new ArrayList<>();
    }
    return new URIBuilder().setScheme(scheme).setHost(host).setPort(port).setPath(path)
        .setParameters(parameters)
        .build();
  }

  private HttpHeaders generateRequestHeaders(HttpServletRequest request) throws Exception {
    HttpHeaders headers = new HttpHeaders();
    Enumeration<String> headerNames = request.getHeaderNames();
    while (headerNames.hasMoreElements()) {
      String headerName = headerNames.nextElement();
      String headerValue = request.getHeader(headerName);
      headers.add(headerName, headerValue);
    }
    return headers;
  }

  private void generateResponseHeaders(HttpHeaders responseHeaders, HttpServletResponse response)
      throws Exception {
    for (Map.Entry<String, List<String>> responseHeader : responseHeaders.entrySet()) {
      String headerName = responseHeader.getKey();
      String headerValue = responseHeader.getValue().toString().replace("[", "").replace("]", "");
      response.addHeader(headerName, headerValue);
    }
  }

  private List<NameValuePair> generateParameters(HttpServletRequest request) throws Exception {
    List<NameValuePair> parameters = new ArrayList<>();
    Enumeration<String> parameterNames = request.getParameterNames();
    while (parameterNames.hasMoreElements()) {
      String parameterName = parameterNames.nextElement();
      String parameterValue = request.getParameter(parameterName);
      parameters.add(new BasicNameValuePair(parameterName, parameterValue));
    }
    return parameters;
  }

  public String invoke(HttpMethod method, String scheme, String host, Integer port, String path,
      String requestBody) throws Exception {
    URI uri = this.generateUri(null, scheme, host, port, path);
    HttpEntity<String> entity = new HttpEntity<String>(requestBody);
    ResponseEntity<String> responseEntity = this.restTemplate
        .exchange(uri, method, entity, String.class);
    return responseEntity.getBody();
  }

  public String invoke(HttpMethod method, HttpServletRequest request, HttpServletResponse response,
      String scheme,
      String host, Integer port, String path, String requestBody) throws Exception {
    URI uri = this.generateUri(this.generateParameters(request), scheme, host, port, path);
    HttpHeaders requestHeaders = this.generateRequestHeaders(request);
    HttpEntity<String> entity = new HttpEntity<String>(requestBody, requestHeaders);
    ResponseEntity<String> responseEntity = this.restTemplate
        .exchange(uri, method, entity, String.class);
    this.generateResponseHeaders(responseEntity.getHeaders(), response);
    return responseEntity.getBody();
  }

}
