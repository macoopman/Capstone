package capstone.repositories;

import capstone.domain.Professor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepository extends PagingAndSortingRepository<Professor, Long> {
}
