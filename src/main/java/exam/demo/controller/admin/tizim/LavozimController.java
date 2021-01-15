package exam.demo.controller.admin.tizim;

import exam.demo.entity.Country;
import exam.demo.entity.tizim.Lavozim;
import exam.demo.payload.admin.CountryRequest;
import exam.demo.payload.Result;
import exam.demo.payload.admin.tizim.LavozimRequest;
import exam.demo.service.tizim.LavozimService;
import exam.demo.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
@RequestMapping("/admin/lavozim")
public class LavozimController {

    @Autowired
    private LavozimService lavozimService;
    @GetMapping(value = {"","/"})
    public String index(Model model,@RequestParam(value = "size",defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
                        @RequestParam(value = "page",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                        @RequestParam(value = "search",defaultValue = "") String search ){

        if (page<0){
            page=0;
        }
        if (size>AppConstants.MAX_PAGE_SIZE){
            size=AppConstants.MAX_PAGE_SIZE;
        }
        if (!search.isEmpty()){
            Page<Lavozim> lavozimPage= lavozimService.search(search);
            model.addAttribute("lavozimPage",lavozimPage);
            int totalPage=lavozimPage.getTotalPages();

            model.addAttribute("totalPage",totalPage);
            if (totalPage>0){
                List<Integer> pageNumber= IntStream.rangeClosed(1,totalPage).boxed().collect(Collectors.toList());
                System.out.println(pageNumber);
                model.addAttribute("pageNumber",pageNumber);
            }

            return "admin/lavozim/index";
        }

        Page<Lavozim> lavozimPage= lavozimService.getAll(page,size);
        model.addAttribute("lavozimPage",lavozimPage);
        int totalPage=lavozimPage.getTotalPages();
        model.addAttribute("totalPage",totalPage);
        if (totalPage>0){
            List<Integer> pageNumber= IntStream.rangeClosed(1,totalPage).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumber",pageNumber);
        }
        return "admin/lavozim/index";
    }

    @GetMapping(value = {"/add"})
    public String addLavozim(Model model){
                     model.addAttribute("lavozimRequest",new LavozimRequest());
                     model.addAttribute("savePath","/admin/lavozim/add");
                     model.addAttribute("result",new Result(false,""));
        return  "admin/lavozim/add";
    }

    @PostMapping(value = {"/add"})
    public String addLavozim(@Valid LavozimRequest lavozimRequest, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){

            model.addAttribute("savePath","/admin/lavozim/add");
            model.addAttribute("result",new Result(false,""));
            return "admin/lavozim/add";
        }

        Result result= lavozimService.add(lavozimRequest);
        model.addAttribute("savePath","/admin/lavozim/add");
        model.addAttribute("result",new Result(true,result.getMessage()));
        return "admin/lavozim/add";
    }


    @GetMapping(value = {"/edit/{id}"})
    public String editLavozim(@PathVariable(value = "id") Long id,Model model){
        model.addAttribute("savePath","/admin/lavozim/edit/"+id);
        Optional<Lavozim> lavozimOptional= lavozimService.getLavozim(id);
        if (!lavozimOptional.isPresent()){
            return "redirect:/admin/lavozim";
        }
        LavozimRequest lavozimRequest=new  LavozimRequest(lavozimOptional.get().getName());
        model.addAttribute("lavozimRequest", lavozimRequest);
        model.addAttribute("result",new Result(false,""));
        return "admin/lavozim/edit";
    }

    @PostMapping(value = {"/edit/{id}"})
    public String editLavozim(@PathVariable("id") Long id,
                              @Valid LavozimRequest lavozimRequest,
                              BindingResult bindingResult,Model model  ){
        if (bindingResult.hasErrors()){
            model.addAttribute("savePath","/admin/lavozim/edit/"+id);
            model.addAttribute("result",new Result(false,""));
            return "admin/lavozim/edit";
        }
       Result result= lavozimService.editLavozim(id,lavozimRequest) ;
        model.addAttribute("savePath","/admin/lavozim/edit/"+id);
        model.addAttribute("lavozimRequest",new LavozimRequest());
        model.addAttribute("result",new Result(true,result.getMessage()));
        return "admin/lavozim/edit";
    }


    @GetMapping(value = {"/delete/{id}"})
    public String deleteLavozim(@PathVariable(value = "id") Long id,Model model){
        model.addAttribute("lavozimRequest", lavozimService.getLavozim(id));
        model.addAttribute("savePath","/admin/lavozim/delete/"+id);
        model.addAttribute("result",new Result(false,""));
        return "admin/lavozim/delete";
    }

    @PostMapping(value = {"/delete/{id}"})
    public String deleteLavozimA(@PathVariable("id") Long id, Model model){

        model.addAttribute("lavozimRequest", new LavozimRequest());
        model.addAttribute("savePath","/admin/lavozim/delete/"+id);
        Result result= lavozimService.delete(id);
        model.addAttribute("result",new Result(true,result.getMessage()));
        return "admin/lavozim/delete";
    }
}


