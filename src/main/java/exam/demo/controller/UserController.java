package exam.demo.controller;

import exam.demo.entity.User;
import exam.demo.payload.Result;
import exam.demo.payload.admin.UserEdit;
import exam.demo.payload.admin.UserRequest;
import exam.demo.security.CurrentUser;
import exam.demo.service.TelegramService;
import exam.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TelegramService telegramService;

    @GetMapping(value = {"","/"})
    public String getIndex(@CurrentUser User user, Model model){
        model.addAttribute("admin","admin");
        model.addAttribute("newDoc",telegramService.getAllDocs(user).stream().filter(attachment -> { return attachment.isOpen()==false;})
        .collect(Collectors.toList())   );
        model.addAttribute("newImg",telegramService.allPictures(user).stream().
                filter(attachment -> {return attachment.isOpen()==false; }).collect(Collectors.toList()));
        return "user/index";
    }
    @GetMapping(value = "/edit")
    public  String edit(@CurrentUser User user, Model model){

        model.addAttribute("savePath","/user/edit");
        UserEdit userEdit=new UserEdit();
        userEdit.setFullName(user.getFullName());
        userEdit.setUserName(user.getUsername());
        model.addAttribute("userEdit",userEdit);
        model.addAttribute("result",new Result(false,""));
        return "user/profile/edit";
    }

    @PostMapping(value = "/edit")
    public String edit(@CurrentUser User user, @Valid UserEdit userEdit, BindingResult result, Model model){
        if (result.hasErrors() || !userEdit.isValid()){
            model.addAttribute("savePath","/user/edit");
            if (!userEdit.isValid()) {
                model.addAttribute("result",  new Result(true, "Parollar bir xil emas"));
            } else {
                model.addAttribute("result",new Result(false, ""));
            }

            return "user/profile/edit";
        }

        model.addAttribute("result",new Result(true,true,userService.EditSelf(userEdit,user).getMessage()));
        return "user/profile/edit";
    }


}
