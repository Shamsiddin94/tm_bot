package exam.demo.controller.quiz;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import exam.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    private UserService userService;

    @GetMapping(value = {"","/"})
    public String index() {

        return "quiz/index";
    }

    @GetMapping("/api")
    @ResponseBody
    public ResponseEntity<?> getData() {
        //System.out.println(userService.getAll().toString());
        return  ResponseEntity.status(200).body(userService.getAll());
    }

}
