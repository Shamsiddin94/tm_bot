package exam.demo.entity.quiz;

import exam.demo.entity.identity.AbsEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class BlankQuestion  extends AbsEntity {

    private Long num;

    private String textNumber;

    private String text;

    @Enumerated(EnumType.STRING)
    private QuizType type;



    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BlankAnswer> answers;

}
