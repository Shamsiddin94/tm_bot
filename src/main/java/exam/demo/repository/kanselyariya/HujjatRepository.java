package exam.demo.repository.kanselyariya;

import exam.demo.entity.User;
import exam.demo.entity.hujjat.Hujjat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HujjatRepository extends JpaRepository<Hujjat,Long> {

    Page<Hujjat> findByDocNumberIsLikeOrRegNumberIsLikeOrMazmuniIsLike(Pageable pageable, String docNumber,
          String regNumber,   String mazmuni);
    Page<Hujjat> findAll(Pageable  pageable);
}
