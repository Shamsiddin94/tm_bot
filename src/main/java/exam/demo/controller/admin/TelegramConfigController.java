package exam.demo.controller.admin;

import exam.demo.entity.bot.Client;
import exam.demo.payload.Result;
import exam.demo.payload.admin.ClientRequest;
import exam.demo.service.TelegramService;
import exam.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/admin/client")
public class TelegramConfigController {

    @Autowired
    private TelegramService service;
    @Autowired
    private UserService userService;

    @GetMapping(value = {"/"})
    public String index(Model model){
        model.addAttribute("clients",service.allClients());
        return "admin/telegram/index";
    }
    @GetMapping(value = "/add")
    public String add(Model model){
        model.addAttribute("clientRequest",new ClientRequest());
        model.addAttribute("savePath","/admin/client/add");
        model.addAttribute("users",userService.getAll());
        model.addAttribute("result",new Result(false,""));
        return "admin/telegram/add";
    }
    @PostMapping(value = "/add")
    public String add(@Valid ClientRequest request, BindingResult bindingResult, Model model){
        model.addAttribute("users",userService.getAll());

        if (bindingResult.hasErrors()){
           model.addAttribute("savePath","/admin/client/add");
           model.addAttribute("result",new Result(false,""));
           return "admin/telegram/add";
       }
        Result result=service.addClient(request);
        model.addAttribute("savePath","/admin/client/add");
        model.addAttribute("result",new Result(true,result.getMessage()));
        return "admin/telegram/add";
    }

    @GetMapping(value = "/edit/{id}")
    public String edit(@PathVariable(value = "id") Long id,  Model model){
        Client client=service.clientOpt(id).get();
        model.addAttribute("clientRequest",
                new ClientRequest(client.getChatId(),client.getUserName(),client.getFirstName(),
                client.getLastName(),client.getUser().getId())
                );
        model.addAttribute("savePath","/admin/client/edit/"+id);
        model.addAttribute("users",userService.getAll());
        model.addAttribute("result",new Result(false,""));
        return "admin/telegram/edit";
    }
    @PostMapping(value = "/edit/{id}")
    public String edit(@PathVariable(value = "id") Long id,
            @Valid ClientRequest request,
            BindingResult bindingResult, Model model){
        model.addAttribute("users",userService.getAll());

        if (bindingResult.hasErrors()){
            model.addAttribute("savePath","/admin/client/edit/"+id);
            model.addAttribute("result",new Result(false,""));
            return "admin/telegram/edit";
        }
        Result result=service.editClient(id,request);
        model.addAttribute("savePath","/admin/client/edit/"+id);
        model.addAttribute("result",new Result(true,result.getMessage()));
        return "admin/telegram/edit";
    }


}
