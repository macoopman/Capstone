package capstone.repositories;

import capstone.domain.Klass;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Repository;

@Repository
@Secured({"ROLE_ADMIN", "ROLE_USER"})
public interface KlassRepository extends CrudRepository<Klass, Long> {

   // this is how I lock down functions
   @Override
   @Secured({"ROLE_ADMIN"})
   <S extends Klass> S save(S s);
}
