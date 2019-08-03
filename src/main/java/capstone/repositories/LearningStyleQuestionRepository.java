package capstone.repositories;

import capstone.domain.LearningStyleQuestion;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface LearningStyleQuestionRepository extends PagingAndSortingRepository<LearningStyleQuestion, Long> {
}
