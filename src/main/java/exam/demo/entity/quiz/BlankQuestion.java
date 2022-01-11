package exam.demo.entity.quiz;

import exam.demo.entity.identity.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BlankQuestion  extends AbsEntity {

    private Long num;

    private String textNumber;

    @Enumerated(EnumType.STRING)
    private QuizType type;

    private String text;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BlankAnswer> answers;

}
