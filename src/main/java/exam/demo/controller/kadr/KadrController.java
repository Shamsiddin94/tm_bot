package exam.demo.controller.kadr;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/kadr")
public class KadrController {
    @GetMapping(value = {"/",""})
    public String index(Model model){

        return "kadr/index";
    }
}
