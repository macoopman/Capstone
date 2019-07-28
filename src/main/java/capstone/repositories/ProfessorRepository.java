package capstone.repositories;

import capstone.domain.Professor;
import capstone.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfessorRepository extends PagingAndSortingRepository<Professor, Long> {
   Optional<Professor> findProfessorByEmail(String email);

}
