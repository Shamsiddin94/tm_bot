package exam.demo.controller;

import exam.demo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@Controller
public class AuthController {
    @Autowired
    AuthService authService;


    @RequestMapping(value = "/login")
    public String getLoginPage(Model model, @RequestParam(value = "error",required = false) String error) {
        if (authService.isAuthenticate()) {

            if (error!=null){
                String errors="Login yoki parol xato kiritilgan" ;
                model.addAttribute("errors",errors);
            }

            return "login";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if (roles.contains("ROLE_ADMIN")) {
            return "redirect:/admin";
        } else if (roles.contains("ROLE_RAHBARIYAT")) {
            return "redirect:/rahbariyat";
        } else if (roles.contains("ROLE_SPIKER")) {
            return "redirect:/spiker";
        } else if (roles.contains("ROLE_MUROJAAT")) {
            return "redirect:/murojaat";
        } else if (roles.contains("ROLE_KANSELYARIYA")) {
            return "redirect:/kanselyariya";
        }else if (roles.contains("ROLE_USER")) {
            return "redirect:/user";
        }else if (roles.contains("ROLE_KADR")) {
            return "redirect:/kadr";
        }
        return "index";
    }
}
