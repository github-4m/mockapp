package id.co.blogspot.fathan.repository.user;

import id.co.blogspot.fathan.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/** Created by fathan.mustaqiim on 10/24/2016. */
public interface UserRepository extends JpaRepository<User, String> {

  User findByUsernameAndPasswordAndMarkForDeleteFalse(String username, String password)
      throws Exception;

  User findByUsername(String username) throws Exception;
}
