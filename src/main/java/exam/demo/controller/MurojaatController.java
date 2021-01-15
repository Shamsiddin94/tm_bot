package exam.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/murojaat")
public class MurojaatController {
    @GetMapping(value = {"","/"})
    public String getIndex(Model model){
        model.addAttribute("admin","admin");
        return "murojaat/index";
    }
}
