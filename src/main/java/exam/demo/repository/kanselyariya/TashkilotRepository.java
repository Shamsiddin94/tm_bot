package exam.demo.repository.kanselyariya;

import exam.demo.entity.workplan.Tashkilot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TashkilotRepository extends JpaRepository<Tashkilot,Long> {
}
