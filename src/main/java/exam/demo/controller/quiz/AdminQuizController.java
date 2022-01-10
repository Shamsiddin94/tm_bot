package exam.demo.controller.quiz;

import com.fasterxml.jackson.annotation.JsonBackReference;
import exam.demo.controller.quiz.payload.BlankFormRequest;
import exam.demo.entity.quiz.BlankForm;
import exam.demo.entity.quiz.QuizType;
import exam.demo.payload.Result;
import exam.demo.payload.ResultModel;
import exam.demo.service.quizService.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller()
@RequestMapping("/admin/quiz")
public class AdminQuizController {

    @Autowired
    private QuizService quizService;

    /*Blankform*/
    @GetMapping(value = {"", "/"})
    public String index(Model model) {
        model.addAttribute("blankForms", quizService.getAll());

        return "admin/quiz/index";
    }

    @GetMapping("/add")
    public String addBlankForm(Model model) {
        model.addAttribute("blankFormRequest", new BlankFormRequest());
        model.addAttribute("savePath", "/admin/quiz/add");
        //model.addAttribute("users",quizService.getAll());
        model.addAttribute("result", new Result(false, ""));
        return "admin/quiz/add";
    }


    @PostMapping("/add")

    public String addQuiz(@Valid BlankFormRequest blankFormRequest,
                          BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("savePath", "/admin/quiz/add");
            model.addAttribute("result", new Result(false, ""));
            return "admin/quiz/add";
        }
        model.addAttribute("blankFormRequest", new BlankFormRequest());
        ResultModel resultModel = quizService.saveBlankForm(blankFormRequest);
        model.addAttribute("savePath", "/admin/quiz/add");
        model.addAttribute("result", resultModel);


        return "admin/quiz/add";
    }

    /* //Blank form*/

    /*    Question*/
    @GetMapping("/question")
    public String questionIndex(Model model) {

        return "admin/quiz/question/index";
    }


    /*Admin quiz api*/

    @GetMapping("/api/allBlanks")
    @ResponseBody
    public ResponseEntity<?> getData() {
        //System.out.println(userService.getAll().toString());
        List<QuizType> types = new ArrayList();
        types.add(QuizType.SHORT);
        types.add(QuizType.CHECKBOX);
        types.add(QuizType.SELECT);
        return ResponseEntity.status(200).body(types);
    }

    @JsonBackReference
    @GetMapping("/api/all")
    @ResponseBody
    public List<BlankForm> getDataAll() {
        List<BlankForm> blankForms = quizService.getAll();
        return blankForms;
    }

}
