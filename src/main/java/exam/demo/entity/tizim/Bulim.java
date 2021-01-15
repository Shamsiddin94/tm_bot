package exam.demo.entity.tizim;

import exam.demo.entity.User;
import exam.demo.entity.identity.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Bulim extends AbsEntity {
    private String name;

    @OneToMany(fetch = FetchType.LAZY)
    private List<User> users;


    public Bulim(String name) {
        this.name=name;
    }
}
