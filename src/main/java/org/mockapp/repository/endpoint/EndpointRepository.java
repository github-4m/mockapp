package org.mockapp.repository.endpoint;

import java.util.List;
import org.mockapp.entity.Endpoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by fathan.mustaqiim on 3/12/2017.
 */
public interface EndpointRepository extends JpaRepository<Endpoint, String> {

  String QUERY_ENABLE_BY_CODE = "UPDATE Endpoint e SET e.enable = TRUE WHERE e.code = ?1";
  String QUERY_DISABLE_BY_CODE = "UPDATE Endpoint e SET e.enable = FALSE WHERE e.code = ?1";
  String QUERY_DELETE_BY_CODE = "UPDATE Endpoint e SET e.markForDelete = TRUE WHERE e.code = ?1";
  String QUERY_DELETE_BY_MODULE_CODE = "UPDATE Endpoint e SET e.markForDelete = TRUE WHERE e.moduleCode = ?1";

  Endpoint findByCodeAndMarkForDeleteFalse(String code) throws Exception;

  List<Endpoint> findByModuleCodeAndMarkForDeleteFalse(String moduleCode) throws Exception;

  @Query(value = EndpointRepository.QUERY_ENABLE_BY_CODE)
  @Modifying(clearAutomatically = true)
  void enableByCode(String code) throws Exception;

  @Query(value = EndpointRepository.QUERY_DISABLE_BY_CODE)
  @Modifying(clearAutomatically = true)
  void disableByCode(String code) throws Exception;

  @Query(value = EndpointRepository.QUERY_DELETE_BY_CODE)
  @Modifying(clearAutomatically = true)
  void deleteByCode(String code) throws Exception;

  @Query(value = EndpointRepository.QUERY_DELETE_BY_MODULE_CODE)
  @Modifying(clearAutomatically = true)
  void deleteByModuleCode(String moduleCode) throws Exception;

}
