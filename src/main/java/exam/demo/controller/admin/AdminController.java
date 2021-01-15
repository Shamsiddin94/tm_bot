package exam.demo.controller.admin;

import exam.demo.entity.User;
import exam.demo.payload.Result;
import exam.demo.payload.admin.UserRequest;
import exam.demo.service.admin.AdminSercive;
import exam.demo.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminSercive adminSercive;


    @GetMapping(value = {"","/"})
    public String getIndex(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            model.addAttribute("admin", username);
        } else {
            String username = principal.toString();
            model.addAttribute("admin", username);
        }

        return "admin/index";
    }

    @GetMapping("/users")
    public String userIndex(Model model,@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
                            @RequestParam(value = "search", defaultValue = "") String search){


        if (page < 0) {
            page = 0;
        }
        if (size > AppConstants.MAX_PAGE_SIZE) {
            size = AppConstants.MAX_PAGE_SIZE;
        }
        if (!search.isEmpty()){
            Page<User> userPage = adminSercive.getUsersSearch(search);
            model.addAttribute("userPage", userPage);
            int totalPage = userPage.getTotalPages();
//            System.out.println(totalPage);
            model.addAttribute("totalPage", totalPage);
            if (totalPage > 0) {
                List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
            //    System.out.println(pageNumbers.toString());
                model.addAttribute("pageNumber", pageNumbers);
            }

            return "admin/users/index";

        }

        Page<User> userPage = adminSercive.getUsers(page, size);
        model.addAttribute("userPage", userPage);
        int totalPage = userPage.getTotalPages();
        model.addAttribute("totalPage", totalPage);

        if (totalPage > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());

            model.addAttribute("pageNumber", pageNumbers);

        }


        return "admin/users/index";

    }






    @GetMapping(value = "/users/add")
    public  String addUser(Model model){

        model.addAttribute("savePath","/admin/users/add");
        model.addAttribute("userRequest",new UserRequest());
        model.addAttribute("roles",adminSercive.getRoles());
        model.addAttribute("result",new Result(false,""));

    return "admin/users/add";
    }

    @PostMapping(value = "/users/add")
    public String addUser(@Valid UserRequest request,BindingResult bindingResult,Model model){

           if (bindingResult.hasErrors()){
               model.addAttribute("roles", adminSercive.getRoles());
               model.addAttribute("result",new Result(false,""));
               return "admin/users/add";
           }
        //System.out.println(request.toString());
        Result result=adminSercive.addUser(request);
           model.addAttribute("result",new Result(true,result.getMessage()));
        System.out.println(result);
        return "admin/users/add";
    }


    @GetMapping(value = "/users/edit/{id}")
    public  String editUser(@PathVariable("id") Long id, Model model){

        model.addAttribute("savePath","/admin/users/edit/"+id);
        Optional<User> userOptional=adminSercive.getUser(id);
        if (!userOptional.isPresent()){
            return "redirect:/admin/users";
        }
        User user=userOptional.get();
        UserRequest userRequest=new UserRequest(user.getFullName(),user.getUsername(),user.getRoles().get(0).getId());
        model.addAttribute("userRequest",userRequest);
        model.addAttribute("roles",adminSercive.getRoles());
        model.addAttribute("result",new Result(false,""));

        return "admin/users/edit";
    }

    @PostMapping(value = "/users/edit/{id}")
    public String editUser(@PathVariable Long id, @Valid UserRequest request,BindingResult result,Model model){

        if (result.hasErrors()){
            model.addAttribute("savePath","/admin/users/edit/"+id);
            model.addAttribute("roles", adminSercive.getRoles());
            model.addAttribute("result",new Result(false,""));
            return "admin/users/edit";
        }
       Result result1=adminSercive.editUser(id,request);
        if (!result1.getSuccess()){
            model.addAttribute("savePath","/admin/users/edit/"+id);
            model.addAttribute("roles", adminSercive.getRoles());
            model.addAttribute("result",new Result(true,result1.getMessage()));
            return "admin/users/edit";
        }
        model.addAttribute("result",new Result(true,result1.getMessage()));
        return "redirect:/admin/users";
    }


    @GetMapping(value = {"/users/delete/{id}"})
    public String deleteUser(@PathVariable("id") Long id, Model model){
        Optional<User> userOptional=adminSercive.getUser(id);
        if (userOptional.isPresent()) {
            model.addAttribute("userRequest",new UserRequest(
                    userOptional.get().getFullName(),
                    userOptional.get().getUsername()
            ) );
            model.addAttribute("savePath", "/admin/users/delete/" + id);
            model.addAttribute("result", new Result(false, ""));
            return "admin/users/delete";
        }
        model.addAttribute("userRequest", new UserRequest());
        model.addAttribute("savePath", "/admin/users/delete/" + id);
        model.addAttribute("result", new Result(false, ""));
        return "admin/users/delete";
        }

        @PostMapping(value = {"/users/delete/{id}"})
    public String delete(@PathVariable("id") Long id,Model model){
        Result result=adminSercive.delete(id);
            model.addAttribute("userRequest",new UserRequest());
            model.addAttribute("savePath","/");
            model.addAttribute("result",new Result(true,result.getMessage()));
            return "redirect:/admin/users";
        }
}