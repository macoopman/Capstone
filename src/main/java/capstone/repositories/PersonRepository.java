package capstone.repositories;

import capstone.domain.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Repository;


@RepositoryRestResource(exported = false)
public interface PersonRepository extends CrudRepository<Person, Long> {
}
