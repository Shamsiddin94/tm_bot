package exam.demo.repository;

import exam.demo.entity.Role;
import exam.demo.entity.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface RoleRepository  extends JpaRepository<Role,Integer> {
    List<Role> findByName(RoleName roleName);
    Optional<Role> findById(Integer id);


}
