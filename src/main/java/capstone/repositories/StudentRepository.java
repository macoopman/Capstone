package capstone.repositories;

import capstone.domain.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {


}

