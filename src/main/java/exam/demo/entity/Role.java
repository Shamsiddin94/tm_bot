package exam.demo.entity;


import exam.demo.entity.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Data
@AllArgsConstructor
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
