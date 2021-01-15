package exam.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/kotib")
public class KotibController {
    @GetMapping(value = {"","/"})
    public  String index(){
        return "kotib/index";
    }
}
