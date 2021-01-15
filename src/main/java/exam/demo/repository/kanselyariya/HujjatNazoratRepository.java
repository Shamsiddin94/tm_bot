package exam.demo.repository.kanselyariya;

import exam.demo.entity.hujjat.Nazorat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HujjatNazoratRepository extends JpaRepository<Nazorat,Long> {
}
