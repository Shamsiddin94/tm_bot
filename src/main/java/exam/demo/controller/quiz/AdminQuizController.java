package exam.demo.controller.quiz;

import exam.demo.controller.quiz.payload.BlankFormModel;
import exam.demo.entity.quiz.QuizType;
import exam.demo.repository.quiz.BlankFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller()
@RequestMapping("/admin/quiz")
public class AdminQuizController {

    @Autowired
    private BlankFormRepository blankFormRepository;


    @GetMapping(value = {"","/"})
    public String index(){


        return "admin/quiz/index";
    }






    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<?> addQuiz(@RequestBody BlankFormModel blankFormModel){

        return  ResponseEntity.status(200).body(blankFormModel);
    }


/*Admin quiz api*/

    @GetMapping("/api/allBlanks")
    @ResponseBody
    public ResponseEntity<?> getData() {
        //System.out.println(userService.getAll().toString());
        List<QuizType> types= new ArrayList();
        types.add(QuizType.SHORT);
        types.add(QuizType.CHECKBOX);
        types.add(QuizType.SELECT);
        return  ResponseEntity.status(200).body(types);
    }


}
