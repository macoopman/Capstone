package capstone.repositories;

import capstone.domain.Session;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Repository;

//@Repository
//@Secured("ROLE_ADMIN")
public interface SessionRepository extends CrudRepository<Session, Long> {

}
