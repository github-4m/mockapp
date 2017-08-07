package org.mockapp.controller.ui.authentication;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by fathan.mustaqiim on 8/7/2017.
 */
@Controller
@RequestMapping(value = AuthenticationUiControllerPath.BASE_PATH)
public class AuthenticationUiController {

  @RequestMapping(value = AuthenticationUiControllerPath.LOGIN, method = RequestMethod.GET)
  public ModelAndView login() throws Exception {
    ModelAndView model = new ModelAndView("login/login");
    return model;
  }

}
