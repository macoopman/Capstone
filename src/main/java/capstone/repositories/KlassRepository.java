package capstone.repositories;

import capstone.domain.Klass;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Repository;

@Repository
public interface KlassRepository extends PagingAndSortingRepository<Klass, Long> {

}
