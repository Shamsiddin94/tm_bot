package exam.demo.entity;


import exam.demo.entity.enums.RoleName;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Data
@Entity
public class Role implements GrantedAuthority {
    @Id
    private Integer id;

    @Enumerated( EnumType.STRING)
    private RoleName name;

    public Role() {
    }

    public Role(RoleName name) {
        this.name = name;
    }
    @Override
    public String getAuthority() {
        return name.name();
    }
}
