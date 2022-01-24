package exam.demo.entity.quiz;

import exam.demo.entity.User;
import exam.demo.entity.identity.AbsEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Quiz extends AbsEntity {

    private String username;

    private String ipAdrees;

    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp timestamp;

    @OneToMany(fetch = FetchType.LAZY)
    private List<BlankForm> blankForms;


    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
