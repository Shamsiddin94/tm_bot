package exam.demo.repository.tizim;

import exam.demo.entity.tizim.Qumita;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QumitaRepository extends JpaRepository<Qumita,Long> {
    Page<Qumita> findByNameIsLike(Pageable pageable, String search);

    Optional<Qumita> findByName(String name);
}
