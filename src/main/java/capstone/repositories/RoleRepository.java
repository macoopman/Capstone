package capstone.repositories;

import capstone.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Secured("ROLE_ADMIN")
public interface RoleRepository extends JpaRepository<Role, Long> {
   Optional<Role> findByRoleName(String name);
}
