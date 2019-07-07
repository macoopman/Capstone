package capstone.repositories;

import capstone.domain.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Repository;

@Repository
@Secured({"ROLE_ADMIN", "ROLE_USER"})
public interface CommentRepository extends CrudRepository<Comment, Long> {
}
