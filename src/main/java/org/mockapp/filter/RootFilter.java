package org.mockapp.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Created by fathan.mustaqiim on 8/7/2017.
 */
public class RootFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse, FilterChain filterChain)
      throws ServletException, IOException {
    if ("/".equals(httpServletRequest.getRequestURI())) {
      httpServletResponse.sendRedirect("/ui/login");
    } else {
      filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
  }
}
