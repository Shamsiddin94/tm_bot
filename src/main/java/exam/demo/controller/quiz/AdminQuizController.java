package exam.demo.controller.quiz;

import exam.demo.controller.quiz.payload.BlankFormModel;
import exam.demo.entity.quiz.BlankForm;
import exam.demo.entity.quiz.QuizType;
import exam.demo.payload.datatable.Page;
import exam.demo.payload.datatable.PageArray;
import exam.demo.payload.datatable.PagingRequest;
import exam.demo.repository.quiz.BlankFormRepository;
import exam.demo.service.quizService.QuizService;
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

    @Autowired
    private QuizService     quizService;

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

    @PostMapping("/api/allBlankForms")
    @ResponseBody
    public Page<BlankForm> getAllBlankForms(@RequestBody PagingRequest pagingRequest) {

        return  quizService.getBlankForms(pagingRequest);
    }


    @PostMapping("/api/allBlankForms/array")
    @ResponseBody
    public PageArray getAllBlankFormArray(@RequestBody PagingRequest pagingRequest) {

        return  quizService.getBlankFormArray(pagingRequest);
    }

}
