package capstone.repositories;

import capstone.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;


// make sure to set this to false in production
@RepositoryRestResource(exported = true)
public interface UserRepository extends JpaRepository<User, Long> {
      Optional<User> findByUsername(String userName);
}
