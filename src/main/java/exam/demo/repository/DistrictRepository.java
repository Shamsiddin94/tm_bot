package exam.demo.repository;

import exam.demo.entity.Country;
import exam.demo.entity.District;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DistrictRepository extends JpaRepository<District,Long> {
    Page<District> findByNameIsLike(Pageable pageable, String search);

    Optional<District> findByName(String name);
}
