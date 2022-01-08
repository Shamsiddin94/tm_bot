package exam.demo.repository.quiz;

import exam.demo.entity.quiz.BlankQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlankQuestionRepository extends JpaRepository<BlankQuestion,Long> {
}
