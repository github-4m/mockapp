package org.mockapp.controller.module;

import java.util.ArrayList;
import java.util.List;
import org.mockapp.dto.BaseResponse;
import org.mockapp.dto.ListBaseResponse;
import org.mockapp.dto.module.CreateModuleRequest;
import org.mockapp.dto.module.ModuleResponse;
import org.mockapp.entity.Module;
import org.mockapp.service.module.ModuleService;
import org.mockapp.util.Precondition;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value = ModuleControllerPath.BASE_PATH)
public class ModuleController {

  @Autowired
  private ModuleService moduleService;

  private Module generateModule(CreateModuleRequest request) throws Exception {
    Module module = new Module();
    BeanUtils.copyProperties(request, module);
    return module;
  }

  private ModuleResponse generateModuleResponse(Module module) throws Exception {
    ModuleResponse moduleResponse = new ModuleResponse();
    BeanUtils.copyProperties(module, moduleResponse);
    return moduleResponse;
  }

  private List<ModuleResponse> generateModuleResponses(List<Module> modules) throws Exception {
    List<ModuleResponse> moduleResponses = new ArrayList<>();
    for (Module module : modules) {
      moduleResponses.add(this.generateModuleResponse(module));
    }
    return moduleResponses;
  }

  @RequestMapping(value = ModuleControllerPath.FILTER, method = RequestMethod.GET)
  public ListBaseResponse<ModuleResponse> filter(@RequestParam String requestId) throws Exception {
    List<Module> modules = this.moduleService.findAll();
    return new ListBaseResponse<>(null, null, true, requestId,
        this.generateModuleResponses(modules));
  }

  @RequestMapping(value = ModuleControllerPath.CREATE, method = RequestMethod.POST, consumes = {
      MediaType.APPLICATION_JSON_VALUE})
  public BaseResponse create(@RequestParam String requestId,
      @RequestBody CreateModuleRequest request) throws Exception {
    Precondition.checkArgument(!StringUtils.isEmpty(request.getName()),
        ModuleControllerErrorMessage.NAME_MUST_NOT_BE_BLANK);
    Precondition.checkArgument(!StringUtils.isEmpty(request.getContextPath()),
        ModuleControllerErrorMessage.CONTEXT_PATH_MUST_NOT_BE_BLANK);
    Module module = this.generateModule(request);
    this.moduleService.create(module);
    return new BaseResponse(null, null, true, requestId);
  }

  @RequestMapping(value = ModuleControllerPath.ENABLE, method = RequestMethod.GET)
  public BaseResponse enable(@RequestParam String requestId, @RequestParam String code)
      throws Exception {
    this.moduleService.enable(code);
    return new BaseResponse(null, null, true, requestId);
  }

  @RequestMapping(value = ModuleControllerPath.DISABLE, method = RequestMethod.GET)
  public BaseResponse disable(@RequestParam String requestId, @RequestParam String code)
      throws Exception {
    this.moduleService.disable(code);
    return new BaseResponse(null, null, true, requestId);
  }

  @RequestMapping(value = ModuleControllerPath.DELETE, method = RequestMethod.GET)
  public BaseResponse delete(@RequestParam String requestId, @RequestParam String code)
      throws Exception {
    this.moduleService.delete(code);
    return new BaseResponse(null, null, true, requestId);
  }

}
