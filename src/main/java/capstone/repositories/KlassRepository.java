package capstone.repositories;

import capstone.domain.Klass;
import capstone.domain.Session;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KlassRepository extends PagingAndSortingRepository<Klass, Long> {
//   Optional<Klass> findKlassBySessionsId(long id);
}
