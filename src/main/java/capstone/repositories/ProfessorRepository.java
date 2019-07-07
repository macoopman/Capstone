package capstone.repositories;

import capstone.domain.Professor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Repository;

@Repository
@Secured({"ROLE_ADMIN", "ROLE_USER"})
public interface ProfessorRepository extends CrudRepository<Professor, Long> {
}
