package exam.demo.repository.tizim;

import exam.demo.entity.tizim.Bulim;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BulimRepository extends JpaRepository<Bulim,Long> {
    Page<Bulim> findByNameIsLike(Pageable pageable, String search);

    Optional<Bulim> findByName(String name);
}
