package exam.demo.repository.kanselyariya;

import exam.demo.entity.workplan.PlanFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HujjatFileRepository extends JpaRepository<PlanFile,Long> {
}
