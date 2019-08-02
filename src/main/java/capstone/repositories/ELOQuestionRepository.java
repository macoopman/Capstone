package capstone.repositories;

import capstone.domain.ELOQuestion;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ELOQuestionRepository extends PagingAndSortingRepository<ELOQuestion, Long> {

}
