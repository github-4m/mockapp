package org.mockapp.repository.module;

import java.util.List;
import org.mockapp.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by 4than.mustaqiim on 3/9/2017.
 */
public interface ModuleRepository extends JpaRepository<Module, String> {

  String QUERY_ENABLE_BY_CODE = "UPDATE Module m SET m.enable = TRUE WHERE m.code = ?1";
  String QUERY_DISABLE_BY_CODE = "UPDATE Module m SET m.enable = FALSE WHERE m.code = ?1";
  String QUERY_DELETE_BY_CODE = "UPDATE Module m SET m.markForDelete = TRUE WHERE m.code = ?1";

  Module findByCodeAndMarkForDeleteFalse(String code) throws Exception;

  List<Module> findByMarkForDeleteFalse() throws Exception;

  @Query(value = ModuleRepository.QUERY_ENABLE_BY_CODE)
  @Modifying(clearAutomatically = true)
  void enableByCode(String code) throws Exception;

  @Query(value = ModuleRepository.QUERY_DISABLE_BY_CODE)
  @Modifying(clearAutomatically = true)
  void disableByCode(String code) throws Exception;

  @Query(value = ModuleRepository.QUERY_DELETE_BY_CODE)
  @Modifying(clearAutomatically = true)
  void deleteByCode(String code) throws Exception;

}
