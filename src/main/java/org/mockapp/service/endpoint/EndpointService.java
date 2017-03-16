package org.mockapp.service.endpoint;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.mockapp.entity.Endpoint;
import org.springframework.http.HttpMethod;

/**
 * Created by fathan.mustaqiim on 3/15/2017.
 */
public interface EndpointService {

  void create(Endpoint endpoint) throws Exception;

  List<Endpoint> findByModuleCode(String moduleCode) throws Exception;

  void enable(String code) throws Exception;

  void disable(String code) throws Exception;

  void delete(String code) throws Exception;

  String mockup(HttpMethod method, HttpServletRequest request, HttpServletResponse response,
      String path, String requestBody) throws Exception;

}
