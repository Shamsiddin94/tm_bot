package exam.demo.controller.admin;

import exam.demo.entity.Country;
import exam.demo.payload.admin.CountryRequest;
import exam.demo.payload.Result;
import exam.demo.service.admin.CountryService;
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
@RequestMapping("/admin/country")
public class CountryController {

    @Autowired
    private CountryService countryService;
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
            Page<Country> countryPage=countryService.search(search);
            model.addAttribute("countryPage",countryPage);
            int totalPage=countryPage.getTotalPages();

            model.addAttribute("totalPage",totalPage);
            if (totalPage>0){
                List<Integer> pageNumber= IntStream.rangeClosed(1,totalPage).boxed().collect(Collectors.toList());
                System.out.println(pageNumber);
                model.addAttribute("pageNumber",pageNumber);
            }

            return "admin/country/index";
        }

        Page<Country> countryPage=countryService.getCountries(page,size);
        model.addAttribute("countryPage",countryPage);
        int totalPage=countryPage.getTotalPages();
        model.addAttribute("totalPage",totalPage);
        if (totalPage>0){
            List<Integer> pageNumber= IntStream.rangeClosed(1,totalPage).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumber",pageNumber);
        }
        return "admin/country/index";
    }

    @GetMapping(value = {"/add"})
    public String addCountry(Model model){
                     model.addAttribute("countryRequest",new CountryRequest());
                     model.addAttribute("savePath","/admin/country/add");
                     model.addAttribute("result",new Result(false,""));
        return  "admin/country/add";
    }

    @PostMapping(value = {"/add"})
    public String addCountry(@Valid CountryRequest countryRequest, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){

            model.addAttribute("savePath","/admin/country/add");
            model.addAttribute("result",new Result(false,""));
            return "admin/country/add";
        }

        Result result=countryService.add(countryRequest);
        model.addAttribute("savePath","/admin/country/add");
        model.addAttribute("result",new Result(true,result.getMessage()));
        return "admin/country/add";
    }


    @GetMapping(value = {"/edit/{id}"})
    public String editCountry(@PathVariable(value = "id") Long id,Model model){
        model.addAttribute("savePath","/admin/country/edit/"+id);
        Optional<Country> countryOptional=countryService.getCountry(id);
        if (!countryOptional.isPresent()){
            return "redirect:/admin/country";
        }
        CountryRequest countryRequest=new  CountryRequest(countryOptional.get().getName(),
                countryOptional.get().getDescription());
        model.addAttribute("countryRequest", countryRequest);
        model.addAttribute("result",new Result(false,""));
        return "admin/country/edit";
    }

    @PostMapping(value = {"/edit/{id}"})
    public String editCountry(@PathVariable("id") Long id,
                              @Valid CountryRequest countryRequest,
                              BindingResult bindingResult,Model model  ){
        if (bindingResult.hasErrors()){
            model.addAttribute("savePath","/admin/country/edit/"+id);
            model.addAttribute("result",new Result(false,""));
            return "admin/country/edit";
        }
       Result result= countryService.editCountry(id,countryRequest) ;
        model.addAttribute("savePath","/admin/country/edit/"+id);
        model.addAttribute("countryRequest",new CountryRequest());
        model.addAttribute("result",new Result(true,result.getMessage()));
        return "admin/country/edit";
    }


    @GetMapping(value = {"/delete/{id}"})
    public String deleteCountry(@PathVariable(value = "id") Long id,Model model){
        model.addAttribute("countryRequest",countryService.getCountry(id));
        model.addAttribute("savePath","#");
        model.addAttribute("result",new Result(false,""));
        return "admin/country/delete";
    }

    @PostMapping(value = {"/delete/{id}"})
    public String deleteCountryA(@PathVariable("id") Long id, Model model){

        model.addAttribute("countryRequest", new CountryRequest());
        model.addAttribute("savePath","/admin/country/delete/"+id);
        Result result=countryService.delete(id);
        model.addAttribute("result",new Result(true,result.getMessage()));
        return "admin/country/delete";
    }
}


