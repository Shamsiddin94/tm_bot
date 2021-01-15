package exam.demo.repository;

import exam.demo.entity.User;
import exam.demo.entity.enums.EntityStatus;
import exam.demo.repository.specs.UsersSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

Optional<User> findByUserName(String userName);
Optional<User> findById(Long id);
List<User> findAllByState(EntityStatus status);
Page<User> findAllByState(EntityStatus status,Pageable pageable);

    @Query("SELECT u FROM users u WHERE u.userName LIKE %?1%"
            + " OR u.fullName LIKE %?1%"
            + " OR CONCAT(u.id, '') LIKE %?1% " +
            "AND CONCAT( u.state,'') LIKE %?2%")
   Page<User> search(String keyword,EntityStatus status, Pageable pageable);

}
