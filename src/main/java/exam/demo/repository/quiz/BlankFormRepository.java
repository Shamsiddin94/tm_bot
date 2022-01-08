package exam.demo.repository.quiz;

import exam.demo.entity.enums.EntityStatus;
import exam.demo.entity.quiz.BlankForm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlankFormRepository  extends JpaRepository<BlankForm,Long> {

    List<BlankForm> getAllByState(EntityStatus status);
}
