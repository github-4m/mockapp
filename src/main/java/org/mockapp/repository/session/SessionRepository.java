package org.mockapp.repository.session;

import org.mockapp.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by fathan.mustaqiim on 10/27/2016.
 */
public interface SessionRepository extends JpaRepository<Session, String> {

  Session findByUsername(String username) throws Exception;

  Session findByUsernameAndSessionId(String username, String sessionId) throws Exception;
}
