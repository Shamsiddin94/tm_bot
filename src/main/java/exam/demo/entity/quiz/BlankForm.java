package exam.demo.entity.quiz;

import exam.demo.entity.identity.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BlankForm extends AbsEntity {
    private String name;

    @Lob
    private String description;



    @Enumerated(EnumType.STRING)
    private QuizState blankState;



    //@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @OneToMany(fetch = FetchType.LAZY)
    private List<BlankQuestion> questions;

}
