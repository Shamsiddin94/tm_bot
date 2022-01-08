package exam.demo.controller.quiz.payload;

import exam.demo.entity.quiz.BlankQuestion;
import exam.demo.entity.quiz.QuizState;
import exam.demo.entity.quiz.QuizType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlankFormRequest {

    @NotEmpty(message = "Ushbu maydon to'ldirilishi kerak")
    private String name;

    @NotEmpty(message = "Ushbu maydon to'ldirilishi kerak")
    private String description;


    private QuizState blankState;

    private List<BlankQuestion> questions;


}
