package exam.demo.controller.workplan;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/plan")
public class WorkPlanController {

    @GetMapping(value = {"" ,"/"})
    public String index() {


        return "plan/index";
    }

}
