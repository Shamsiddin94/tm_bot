package exam.demo.controller.admin.tizim;

import exam.demo.entity.Country;
import exam.demo.entity.tizim.Fraksiya;
import exam.demo.payload.admin.CountryRequest;
import exam.demo.payload.Result;
import exam.demo.payload.admin.tizim.FraksiyaRequest;
import exam.demo.service.tizim.FraksiyaService;
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
@RequestMapping("/admin/fraksiya")
public class FraksiyaController {

    @Autowired
    private FraksiyaService service;
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
            Page<Fraksiya> fraksiyaPage= service.search(search);
            model.addAttribute("fraksiyaPage",fraksiyaPage);
            int totalPage=fraksiyaPage.getTotalPages();

            model.addAttribute("totalPage",totalPage);
            if (totalPage>0){
                List<Integer> pageNumber= IntStream.rangeClosed(1,totalPage).boxed().collect(Collectors.toList());
                System.out.println(pageNumber);
                model.addAttribute("pageNumber",pageNumber);
            }

            return "admin/fraksiya/index";
        }

        Page<Fraksiya> fraksiyaPage= service.getAll(page,size);
        model.addAttribute("fraksiyaPage",fraksiyaPage);
        int totalPage=fraksiyaPage.getTotalPages();
        model.addAttribute("totalPage",totalPage);
        if (totalPage>0){
            List<Integer> pageNumber= IntStream.rangeClosed(1,totalPage).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumber",pageNumber);
        }
        return "admin/fraksiya/index";
    }

    @GetMapping(value = {"/add"})
    public String addFraksiya(Model model){
                     model.addAttribute("fraksiyaRequest",new FraksiyaRequest());
                     model.addAttribute("savePath","/admin/fraksiya/add");
                     model.addAttribute("result",new Result(false,""));
        return  "admin/fraksiya/add";
    }

    @PostMapping(value = {"/add"})
    public String addFraksiya(@Valid FraksiyaRequest fraksiyaRequest, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){

            model.addAttribute("savePath","/admin/fraksiya/add");
            model.addAttribute("result",new Result(false,""));
            return "admin/fraksiya/add";
        }

        Result result= service.add(fraksiyaRequest);
        model.addAttribute("savePath","/admin/fraksiya/add");
        model.addAttribute("result",new Result(true,result.getMessage()));
        return "admin/fraksiya/add";
    }


    @GetMapping(value = {"/edit/{id}"})
    public String editFraksiya(@PathVariable(value = "id") Long id,Model model){
        model.addAttribute("savePath","/admin/fraksiya/edit/"+id);
        Optional<Fraksiya> fraksiyaOptional= service.getFraksiya(id);
        if (!fraksiyaOptional.isPresent()){
            return "redirect:/admin/fraksiya";
        }
        FraksiyaRequest fraksiyaRequest=new  FraksiyaRequest(fraksiyaOptional.get().getName());
        model.addAttribute("fraksiyaRequest", fraksiyaRequest);
        model.addAttribute("result",new Result(false,""));
        return "admin/fraksiya/edit";
    }

    @PostMapping(value = {"/edit/{id}"})
    public String editFraksiya(@PathVariable("id") Long id,
                              @Valid FraksiyaRequest fraksiyaRequest,
                              BindingResult bindingResult,Model model  ){
        if (bindingResult.hasErrors()){
            model.addAttribute("savePath","/admin/fraksiya/edit/"+id);
            model.addAttribute("result",new Result(false,""));
            return "admin/fraksiya/edit";
        }
       Result result= service.editFraksiya(id,fraksiyaRequest) ;
        model.addAttribute("savePath","/admin/fraksiya/edit/"+id);
        model.addAttribute("fraksiyaRequest",new FraksiyaRequest());
        model.addAttribute("result",new Result(true,result.getMessage()));
        return "admin/fraksiya/edit";
    }


    @GetMapping(value = {"/delete/{id}"})
    public String deleteFraksiya(@PathVariable(value = "id") Long id,Model model){
        model.addAttribute("fraksiyaRequest", service.getFraksiya(id));
        model.addAttribute("savePath","/admin/fraksiya/delete/"+id);
        model.addAttribute("result",new Result(false,""));
        return "admin/fraksiya/delete";
    }

    @PostMapping(value = {"/delete/{id}"})
    public String deleteFraksiyaA(@PathVariable("id") Long id, Model model){

        model.addAttribute("fraksiyaRequest", new FraksiyaRequest());
        model.addAttribute("savePath","/admin/fraksiya/delete/"+id);
        Result result= service.delete(id);
        model.addAttribute("result",new Result(true,result.getMessage()));
        return "admin/fraksiya/delete";
    }
}


