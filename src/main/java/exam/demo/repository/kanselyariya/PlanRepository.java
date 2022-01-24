package exam.demo.repository.kanselyariya;

import exam.demo.entity.workplan.Plan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanRepository extends JpaRepository<Plan,Long> {


    Page<Plan> findAll(Pageable  pageable);
}
