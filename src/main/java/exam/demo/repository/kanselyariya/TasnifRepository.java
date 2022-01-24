package exam.demo.repository.kanselyariya;

import exam.demo.entity.workplan.Tasnif;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TasnifRepository extends JpaRepository<Tasnif,Long> {
}
