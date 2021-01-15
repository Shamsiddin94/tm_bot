package exam.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rahbariyat")
public class RahbariyatController {
    @GetMapping(value = {"","/"})
    public String getIndex(Model model){
        model.addAttribute("admin","admin");
        return "rahbariyat/index";
    }
}
