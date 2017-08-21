package org.mockapp.controller.ui.authentication;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by fathan.mustaqiim on 8/9/2017.
 */
@Controller
@RequestMapping(value = AuthenticationUiControllerPath.BASE_PATH)
public class AuthenticationUiController {

  @RequestMapping(value = AuthenticationUiControllerPath.LOGIN)
  public ModelAndView login() throws Exception {
    ModelAndView model = new ModelAndView("login/login");
    return model;
  }

  @RequestMapping(value = AuthenticationUiControllerPath.SIGNUP)
  public ModelAndView register() throws Exception {
    ModelAndView model = new ModelAndView("register/register");
    return model;
  }

}
