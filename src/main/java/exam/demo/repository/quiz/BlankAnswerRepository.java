package exam.demo.repository.quiz;

import exam.demo.entity.quiz.BlankAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlankAnswerRepository  extends JpaRepository<BlankAnswer,Long> {
}
