package org.mockapp.outbound.wiremock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;

/**
 * Created by fathan.mustaqiim on 3/12/2017.
 */
public interface WiremockOutbound {

  void reset() throws Exception;

  String mockup(HttpMethod method, HttpServletRequest request, HttpServletResponse response,
      String path, String requestBody) throws Exception;

}
