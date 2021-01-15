package exam.demo.repository.tizim;

import exam.demo.entity.tizim.Fraksiya;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FraksiyaRepository extends JpaRepository<Fraksiya,Long> {
    Page<Fraksiya> findByNameIsLike(Pageable pageable, String search);

    Optional<Fraksiya> findByName(String name);
}
