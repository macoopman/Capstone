package capstone.repositories;

import capstone.domain.LearningStyleAnswers;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface LearningStyleAnswerRepository extends PagingAndSortingRepository<LearningStyleAnswers, Long> {
}
