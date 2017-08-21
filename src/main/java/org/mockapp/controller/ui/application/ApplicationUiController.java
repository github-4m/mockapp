package org.mockapp.controller.ui.application;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by fathan.mustaqiim on 8/11/2017.
 */
@Controller
@RequestMapping(value = ApplicationUiControllerPath.BASE_PATH)
public class ApplicationUiController {

  @RequestMapping(method = RequestMethod.GET)
  public ModelAndView application() throws Exception {
    ModelAndView model = new ModelAndView("application/application");
    return model;
  }

  @RequestMapping(value = ApplicationUiControllerPath.MODAL, method = RequestMethod.GET)
  public ModelAndView modal() throws Exception {
    ModelAndView model = new ModelAndView("application/modal/modal");
    return model;
  }

}
