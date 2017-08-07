package org.mockapp.controller.ui.module;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by fathan.mustaqiim on 8/7/2017.
 */
@Controller
@RequestMapping(value = ModuleUiControllerPath.BASE_PATH)
public class ModuleUiController {

  @RequestMapping(method = RequestMethod.GET)
  public ModelAndView summary() throws Exception {
    ModelAndView model = new ModelAndView("module/summary");
    return model;
  }

}
