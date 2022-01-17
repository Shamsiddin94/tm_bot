package exam.demo.repository.kanselyariya;

import exam.demo.entity.User;
import exam.demo.entity.hujjat.Bajarish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HujjatBajarishRepository extends JpaRepository<Bajarish,Long> {
    List<Bajarish> findByKimga (User user);

    List<Bajarish> findByKimdan(User user);
}
