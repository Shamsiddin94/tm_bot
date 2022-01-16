package exam.demo.repository.kanselyariya;

import exam.demo.entity.User;
import exam.demo.entity.workplan.Assigning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HujjatBajarishRepository extends JpaRepository<Assigning,Long> {
    List<Assigning> findByKimga (User user);

    List<Assigning> findByKimdan(User user);
}
