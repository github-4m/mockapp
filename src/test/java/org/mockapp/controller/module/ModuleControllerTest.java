package org.mockapp.controller.module;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockapp.dto.module.CreateModuleRequest;
import org.mockapp.entity.Module;
import org.mockapp.service.module.ModuleService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.verification.VerificationMode;

/**
 * Created by fathan.mustaqim on 3/20/2017.
 */
public class ModuleControllerTest {

  private static final String DEFAULT_REQUEST_ID = UUID.randomUUID().toString();
  private static final String DEFAULT_NAME = "Mockapp";
  private static final String DEFAULT_CONTEXT_PATH = "mockapp";
  private static final VerificationMode NEVER_CALLED = Mockito.times(0);

  @Mock
  private ModuleService moduleService;

  @InjectMocks
  private ModuleController moduleController;

  private Module generateModule() throws Exception {
    Module module = new Module();
    return module;
  }

  private List<Module> generateModules() throws Exception {
    List<Module> modules = new ArrayList<>();
    modules.add(this.generateModule());
    return modules;
  }

  private CreateModuleRequest generateCreateModuleRequest() throws Exception {
    CreateModuleRequest request = new CreateModuleRequest();
    request.setName(ModuleControllerTest.DEFAULT_NAME);
    request.setContextPath(ModuleControllerTest.DEFAULT_CONTEXT_PATH);
    return request;
  }

  @Before
  public void initializeTest() throws Exception {
    MockitoAnnotations.initMocks(this);
    List<Module> modules = this.generateModules();
    Mockito.when(this.moduleService.findAll()).thenReturn(modules);
    Mockito.doNothing().when(this.moduleService).create(Mockito.any(Module.class));
    Mockito.doNothing().when(this.moduleService).enable(Mockito.anyString());
    Mockito.doNothing().when(this.moduleService).disable(Mockito.anyString());
    Mockito.doNothing().when(this.moduleService).delete(Mockito.anyString());
  }

  @After
  public void finalizeTest() throws Exception {
    Mockito.verifyNoMoreInteractions(this.moduleService);
  }

  @Test
  public void filterTest() throws Exception {
    this.moduleController.filter(ModuleControllerTest.DEFAULT_REQUEST_ID);
    Mockito.verify(this.moduleService).findAll();
  }

  @Test
  public void createTest() throws Exception {
    this.moduleController
        .create(ModuleControllerTest.DEFAULT_REQUEST_ID, this.generateCreateModuleRequest());
    Mockito.verify(this.moduleService).create(Mockito.any(Module.class));
  }

  @Test(expected = Exception.class)
  public void createWithNameExceptionTest() throws Exception {
    CreateModuleRequest request = this.generateCreateModuleRequest();
    request.setName(null);
    try {
      this.moduleController
          .create(ModuleControllerTest.DEFAULT_REQUEST_ID, request);
    } catch (Exception e) {
      Assert.assertEquals(ModuleControllerErrorMessage.NAME_MUST_NOT_BE_BLANK, e.getMessage());
      Mockito.verify(this.moduleService, ModuleControllerTest.NEVER_CALLED)
          .create(Mockito.any(Module.class));
      throw e;
    }
  }

  @Test(expected = Exception.class)
  public void createWithContextPathExceptionTest() throws Exception {
    CreateModuleRequest request = this.generateCreateModuleRequest();
    request.setContextPath(null);
    try {
      this.moduleController
          .create(ModuleControllerTest.DEFAULT_REQUEST_ID, request);
    } catch (Exception e) {
      Assert.assertEquals(ModuleControllerErrorMessage.CONTEXT_PATH_MUST_NOT_BE_BLANK,
          e.getMessage());
      Mockito.verify(this.moduleService, ModuleControllerTest.NEVER_CALLED)
          .create(Mockito.any(Module.class));
      throw e;
    }
  }

  @Test
  public void enableTest() throws Exception {
    this.moduleController.enable(ModuleControllerTest.DEFAULT_REQUEST_ID, null);
    Mockito.verify(this.moduleService).enable(Mockito.anyString());
  }

  @Test
  public void disableTest() throws Exception {
    this.moduleController.disable(ModuleControllerTest.DEFAULT_REQUEST_ID, null);
    Mockito.verify(this.moduleService).disable(Mockito.anyString());
  }

  @Test
  public void deleteTest() throws Exception {
    this.moduleController.delete(ModuleControllerTest.DEFAULT_REQUEST_ID, null);
    Mockito.verify(this.moduleService).delete(Mockito.anyString());
  }

}
