package exam.demo.controller.admin.tizim;

import exam.demo.entity.Country;
import exam.demo.entity.tizim.Bulim;
import exam.demo.payload.admin.CountryRequest;
import exam.demo.payload.Result;
import exam.demo.payload.admin.tizim.BulimRequest;
import exam.demo.service.tizim.BulimService;
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
@RequestMapping("/admin/bulim")
public class BulimController {

    @Autowired
    private BulimService service;
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
            Page<Bulim> bulimPage= service.search(search);
            model.addAttribute("bulimPage",bulimPage);
            int totalPage=bulimPage.getTotalPages();

            model.addAttribute("totalPage",totalPage);
            if (totalPage>0){
                List<Integer> pageNumber= IntStream.rangeClosed(1,totalPage).boxed().collect(Collectors.toList());
                System.out.println(pageNumber);
                model.addAttribute("pageNumber",pageNumber);
            }

            return "admin/bulim/index";
        }

        Page<Bulim> bulimPage= service.getAll(page,size);
        model.addAttribute("bulimPage",bulimPage);
        int totalPage=bulimPage.getTotalPages();
        model.addAttribute("totalPage",totalPage);
        if (totalPage>0){
            List<Integer> pageNumber= IntStream.rangeClosed(1,totalPage).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumber",pageNumber);
        }
        return "admin/bulim/index";
    }

    @GetMapping(value = {"/add"})
    public String addBulim(Model model){
                     model.addAttribute("bulimRequest",new BulimRequest());
                     model.addAttribute("savePath","/admin/bulim/add");
                     model.addAttribute("result",new Result(false,""));
        return  "admin/bulim/add";
    }

    @PostMapping(value = {"/add"})
    public String addBulim(@Valid BulimRequest bulimRequest, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){

            model.addAttribute("savePath","/admin/bulim/add");
            model.addAttribute("result",new Result(false,""));
            return "admin/bulim/add";
        }

        Result result= service.add(bulimRequest);
        model.addAttribute("savePath","/admin/bulim/add");
        model.addAttribute("result",new Result(true,result.getMessage()));
        return "admin/bulim/add";
    }


    @GetMapping(value = {"/edit/{id}"})
    public String editBulim(@PathVariable(value = "id") Long id,Model model){
        model.addAttribute("savePath","/admin/bulim/edit/"+id);
        Optional<Bulim> bulimOptional= service.getBulim(id);
        if (!bulimOptional.isPresent()){
            return "redirect:/admin/bulim";
        }
        BulimRequest bulimRequest=new  BulimRequest(bulimOptional.get().getName());
        model.addAttribute("bulimRequest", bulimRequest);
        model.addAttribute("result",new Result(false,""));
        return "admin/bulim/edit";
    }

    @PostMapping(value = {"/edit/{id}"})
    public String editBulim(@PathVariable("id") Long id,
                              @Valid BulimRequest bulimRequest,
                              BindingResult bindingResult,Model model  ){
        if (bindingResult.hasErrors()){
            model.addAttribute("savePath","/admin/bulim/edit/"+id);
            model.addAttribute("result",new Result(false,""));
            return "admin/bulim/edit";
        }
       Result result= service.editBulim(id,bulimRequest) ;
        model.addAttribute("savePath","/admin/bulim/edit/"+id);
        model.addAttribute("bulimRequest",new BulimRequest());
        model.addAttribute("result",new Result(true,result.getMessage()));
        return "admin/bulim/edit";
    }


    @GetMapping(value = {"/delete/{id}"})
    public String deleteBulim(@PathVariable(value = "id") Long id,Model model){
        model.addAttribute("bulimRequest", service.getBulim(id).get());
        model.addAttribute("savePath","/admin/bulim/delete/"+id);
        model.addAttribute("result",new Result(false,""));
        return "admin/bulim/delete";
    }

    @PostMapping(value = {"/delete/{id}"})
    public String deleteBulimA(@PathVariable("id") Long id, Model model){

        model.addAttribute("BulimRequest", new BulimRequest());
        model.addAttribute("savePath","/admin/bulim/delete/"+id);
        Result result= service.delete(id);
        model.addAttribute("result",new Result(true,result.getMessage()));
        return "admin/bulim/delete";
    }
}


