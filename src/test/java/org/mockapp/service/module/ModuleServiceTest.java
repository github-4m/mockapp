package org.mockapp.service.module;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockapp.entity.Module;
import org.mockapp.outbound.wiremock.WiremockOutbound;
import org.mockapp.repository.endpoint.EndpointRepository;
import org.mockapp.repository.module.ModuleRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.verification.VerificationMode;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Created by fathan.mustaqim on 3/17/2017.
 */
public class ModuleServiceTest {

  private static final String DEFAULT_RESOURCE_FILE = "mockup/default.json";
  private static final String DEFAULT_CODE = UUID.randomUUID().toString();
  private static final String DEFAULT_ENDPOINT_CODE = UUID.randomUUID().toString();
  private static final VerificationMode NEVER_CALLED = Mockito.times(0);

  @Mock
  private ModuleRepository moduleRepository;

  @Mock
  private EndpointRepository endpointRepository;

  @Mock
  private WiremockOutbound wiremockOutbound;

  @InjectMocks
  private ModuleServiceBean moduleServiceBean;

  private Module generateModule() throws Exception {
    Module module = new Module();
    module.setCode(ModuleServiceTest.DEFAULT_CODE);
    return module;
  }

  private List<Module> generateModules() throws Exception {
    List<Module> modules = new ArrayList<>();
    modules.add(this.generateModule());
    return modules;
  }

  private void generateWiremockDirectory() throws Exception {
    ClassLoader classLoader = this.getClass().getClassLoader();
    File file = new File(classLoader.getResource(ModuleServiceTest.DEFAULT_RESOURCE_FILE).toURI());
    File directory = file.getParentFile();
    ReflectionTestUtils
        .setField(this.moduleServiceBean, "wiremockDirectory", directory.getAbsolutePath());
  }

  private void generateModuleDirectory() throws Exception {
    ClassLoader classLoader = this.getClass().getClassLoader();
    File file = new File(classLoader.getResource(ModuleServiceTest.DEFAULT_RESOURCE_FILE).toURI());
    File directory = file.getParentFile();
    File moduleDirectory = new File(directory, "mappings/" + ModuleServiceTest.DEFAULT_CODE);
    if (!moduleDirectory.exists()) {
      moduleDirectory.mkdirs();
    }
    File endpointFile = new File(moduleDirectory,
        ModuleServiceTest.DEFAULT_ENDPOINT_CODE + ".json");
    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(endpointFile));
    bos.write("data".getBytes());
    bos.close();
  }

  @Before
  public void initializeTest() throws Exception {
    MockitoAnnotations.initMocks(this);
    this.generateWiremockDirectory();
    Module module = this.generateModule();
    List<Module> modules = this.generateModules();
    Mockito.when(this.moduleRepository.save(Mockito.any(Module.class))).thenReturn(null);
    Mockito.when(this.moduleRepository.findByMarkForDeleteFalse()).thenReturn(modules);
    Mockito.when(this.moduleRepository.findByCodeAndMarkForDeleteFalse(Mockito.anyString()))
        .thenReturn(module);
    Mockito.doNothing().when(this.moduleRepository).enableByCode(Mockito.anyString());
    Mockito.doNothing().when(this.moduleRepository).disableByCode(Mockito.anyString());
    Mockito.doNothing().when(this.moduleRepository).deleteByCode(Mockito.anyString());
    Mockito.doNothing().when(this.endpointRepository).deleteByModuleCode(Mockito.anyString());
    Mockito.doNothing().when(this.wiremockOutbound).reset();
  }

  @After
  public void finalizeTest() throws Exception {
    Mockito.verifyNoMoreInteractions(this.moduleRepository);
    Mockito.verifyNoMoreInteractions(this.endpointRepository);
    Mockito.verifyNoMoreInteractions(this.wiremockOutbound);
  }

  @Test
  public void createTest() throws Exception {
    this.moduleServiceBean.create(this.generateModule());
    Mockito.verify(this.moduleRepository).save(Mockito.any(Module.class));
  }

  @Test
  public void findAllTest() throws Exception {
    this.moduleServiceBean.findAll();
    Mockito.verify(this.moduleRepository).findByMarkForDeleteFalse();
  }

  @Test
  public void enableTest() throws Exception {
    this.moduleServiceBean.enable(null);
    Mockito.verify(this.moduleRepository).findByCodeAndMarkForDeleteFalse(Mockito.anyString());
    Mockito.verify(this.moduleRepository).enableByCode(Mockito.anyString());
  }

  @Test(expected = Exception.class)
  public void enableWithExceptionTest() throws Exception {
    Mockito.when(this.moduleRepository.findByCodeAndMarkForDeleteFalse(Mockito.anyString()))
        .thenReturn(null);
    try {
      this.moduleServiceBean.enable(null);
    } catch (Exception e) {
      Mockito.verify(this.moduleRepository).findByCodeAndMarkForDeleteFalse(Mockito.anyString());
      Mockito.verify(this.moduleRepository, ModuleServiceTest.NEVER_CALLED)
          .enableByCode(Mockito.anyString());
      throw e;
    }
  }

  @Test
  public void disableTest() throws Exception {
    this.moduleServiceBean.disable(null);
    Mockito.verify(this.moduleRepository).findByCodeAndMarkForDeleteFalse(Mockito.anyString());
    Mockito.verify(this.moduleRepository).disableByCode(Mockito.anyString());
  }

  @Test(expected = Exception.class)
  public void disableWithExceptionTest() throws Exception {
    Mockito.when(this.moduleRepository.findByCodeAndMarkForDeleteFalse(Mockito.anyString()))
        .thenReturn(null);
    try {
      this.moduleServiceBean.disable(null);
    } catch (Exception e) {
      Mockito.verify(this.moduleRepository).findByCodeAndMarkForDeleteFalse(Mockito.anyString());
      Mockito.verify(this.moduleRepository, ModuleServiceTest.NEVER_CALLED)
          .disableByCode(Mockito.anyString());
      throw e;
    }
  }

  @Test
  public void deleteTest() throws Exception {
    this.generateModuleDirectory();
    this.moduleServiceBean.delete(null);
    Mockito.verify(this.moduleRepository).findByCodeAndMarkForDeleteFalse(Mockito.anyString());
    Mockito.verify(this.moduleRepository).deleteByCode(Mockito.anyString());
    Mockito.verify(this.endpointRepository).deleteByModuleCode(Mockito.anyString());
    Mockito.verify(this.wiremockOutbound).reset();
  }

  @Test
  public void deleteWithoutDeleteModuleDirectoryTest() throws Exception {
    this.moduleServiceBean.delete(null);
    Mockito.verify(this.moduleRepository).findByCodeAndMarkForDeleteFalse(Mockito.anyString());
    Mockito.verify(this.moduleRepository).deleteByCode(Mockito.anyString());
    Mockito.verify(this.endpointRepository).deleteByModuleCode(Mockito.anyString());
    Mockito.verify(this.wiremockOutbound).reset();
  }

  @Test(expected = Exception.class)
  public void deleteWithExceptionTest() throws Exception {
    Mockito.when(this.moduleRepository.findByCodeAndMarkForDeleteFalse(Mockito.anyString()))
        .thenReturn(null);
    try {
      this.moduleServiceBean.delete(null);
    } catch (Exception e) {
      Mockito.verify(this.moduleRepository).findByCodeAndMarkForDeleteFalse(Mockito.anyString());
      Mockito.verify(this.moduleRepository, ModuleServiceTest.NEVER_CALLED)
          .deleteByCode(Mockito.anyString());
      Mockito.verify(this.endpointRepository, ModuleServiceTest.NEVER_CALLED)
          .deleteByModuleCode(Mockito.anyString());
      Mockito.verify(this.wiremockOutbound, ModuleServiceTest.NEVER_CALLED).reset();
      throw e;
    }
  }

}
