package exam.demo.repository.kanselyariya;

import exam.demo.entity.hujjat.Hujjat;
import exam.demo.entity.hujjat.JavobFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JavobFileRepository extends JpaRepository<JavobFile,Long> {
}
