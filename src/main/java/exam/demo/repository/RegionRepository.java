package exam.demo.repository;

import exam.demo.entity.Country;
import exam.demo.entity.District;
import exam.demo.entity.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegionRepository extends JpaRepository<Region,Long> {

    Page<Region> findByNameIsLike(Pageable pageable, String search);

    Optional<Region> findByName(String name);

    List<Region> findByCountry_Id(Long id);
}
