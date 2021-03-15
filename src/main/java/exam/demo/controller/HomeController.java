package exam.demo.controller;

import exam.demo.entity.User;
import exam.demo.security.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.util.Set;

@Controller
@RequestMapping("/")
public class HomeController {



    @GetMapping(value = {"/", "/index", ""})
    public String getAdmin(@CurrentUser User user, Model model) {

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
        }
        return "index";

    }


}
