package exam.demo.controller.quiz.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchQuestionModel {

    private String id;
    private String num;
    private String textNumber;
    private String type;
    private String text;
}
