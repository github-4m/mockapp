package org.mockapp.service.module;

import java.io.File;
import java.util.List;
import org.mockapp.entity.Module;
import org.mockapp.outbound.wiremock.WiremockOutbound;
import org.mockapp.repository.endpoint.EndpointRepository;
import org.mockapp.repository.module.ModuleRepository;
import org.mockapp.util.Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by 4than.mustaqiim on 3/9/2017.
 */
@Service
@Transactional(readOnly = true)
public class ModuleServiceBean implements ModuleService {

  @Autowired
  private ModuleRepository moduleRepository;

  @Autowired
  private EndpointRepository endpointRepository;

  @Autowired
  private WiremockOutbound wiremockOutbound;

  @Value("${wiremock.directory}")
  private String wiremockDirectory;

  private void deleteModuleDirectory(Module module) throws Exception {
    File directory = new File(this.wiremockDirectory + "/mappings/" + module.getCode());
    if (directory.exists()) {
      String[] filenames = directory.list();
      for (String filename : filenames) {
        File file = new File(directory, filename);
        file.delete();
      }
      if (directory.list().length == 0) {
        directory.delete();
      }
    }
  }

  @Override
  @Transactional(readOnly = false, rollbackFor = Exception.class)
  public void create(Module module) throws Exception {
    module.setCode(Generator.generateCode());
    this.moduleRepository.save(module);
  }

  @Override
  public List<Module> findAll() throws Exception {
    return this.moduleRepository.findByMarkForDeleteFalse();
  }

  @Override
  @Transactional(readOnly = false, rollbackFor = Exception.class)
  public void enable(String code) throws Exception {
    Module module = this.moduleRepository.findByCodeAndMarkForDeleteFalse(code);
    if (module == null) {
      throw new Exception("Invalid code");
    }
    this.moduleRepository.enableByCode(code);
  }

  @Override
  @Transactional(readOnly = false, rollbackFor = Exception.class)
  public void disable(String code) throws Exception {
    Module module = this.moduleRepository.findByCodeAndMarkForDeleteFalse(code);
    if (module == null) {
      throw new Exception("Invalid code");
    }
    this.moduleRepository.disableByCode(code);
  }

  @Override
  @Transactional(readOnly = false, rollbackFor = Exception.class)
  public void delete(String code) throws Exception {
    Module module = this.moduleRepository.findByCodeAndMarkForDeleteFalse(code);
    if (module == null) {
      throw new Exception("Invalid code");
    }
    this.moduleRepository.deleteByCode(code);
    this.endpointRepository.deleteByModuleCode(code);
    this.deleteModuleDirectory(module);
    this.wiremockOutbound.reset();
  }
}
