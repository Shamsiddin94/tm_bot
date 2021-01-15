package exam.demo.repository;

import exam.demo.entity.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country,Long> {

    Page<Country> findAll(Pageable pageable);

    Page<Country> findByNameIsLike(Pageable pageable,String search);
    Boolean existsByName(String string);

    Optional<Country> findByName(String name);

    Optional<Country> findById(Long id);
}
