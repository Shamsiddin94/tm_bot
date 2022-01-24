package exam.demo.repository.kanselyariya;

import exam.demo.entity.workplan.PlanAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JavobFileRepository extends JpaRepository<PlanAnswer,Long> {
}
