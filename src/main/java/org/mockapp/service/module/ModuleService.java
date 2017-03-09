package org.mockapp.service.module;

import java.util.List;
import org.mockapp.entity.Module;

/**
 * Created by 4than.mustaqiim on 3/9/2017.
 */
public interface ModuleService {

  void create(Module module) throws Exception;

  List<Module> findAll() throws Exception;

  void enable(String code) throws Exception;

  void disable(String code) throws Exception;

  void delete(String code) throws Exception;

}
