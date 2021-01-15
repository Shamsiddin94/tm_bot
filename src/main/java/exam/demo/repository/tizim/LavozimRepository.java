package exam.demo.repository.tizim;

import exam.demo.entity.tizim.Lavozim;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LavozimRepository extends JpaRepository<Lavozim,Long> {

    Page<Lavozim> findByNameIsLike(Pageable pageable, String search);

    Optional<Lavozim> findByName(String name);
}
