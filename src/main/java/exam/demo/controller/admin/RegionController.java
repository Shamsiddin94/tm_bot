package exam.demo.controller.admin;


import exam.demo.entity.Region;
import exam.demo.payload.admin.RegionRequest;
import exam.demo.payload.Result;
import exam.demo.service.admin.Regionservice;
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
@RequestMapping("/admin/region")
public class RegionController {

    @Autowired
    private Regionservice regionService;


    @GetMapping(value = {"","/"})
    public String index(Model model,
                        @RequestParam(value = "size",defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
                        @RequestParam(value = "page",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                        @RequestParam(value = "search",defaultValue = "") String search ){

        if (page<0){
            page=0;
        }
        if (size>AppConstants.MAX_PAGE_SIZE){
            size=AppConstants.MAX_PAGE_SIZE;
        }
        if (!search.isEmpty()){
            Page<Region> regionPage=regionService.search(search);
            model.addAttribute("regionPage",regionPage);
            int totalPage=regionPage.getTotalPages();

            model.addAttribute("totalPage",totalPage);
            if (totalPage>0){
                List<Integer> pageNumber= IntStream.rangeClosed(1,totalPage).boxed().collect(Collectors.toList());
                System.out.println(pageNumber);
                model.addAttribute("pageNumber",pageNumber);
            }

            return "admin/region/index";
        }

        Page<Region> regionPage=regionService.getRegions(page,size);
        model.addAttribute("regionPage",regionPage);
        int totalPage=regionPage.getTotalPages();
        model.addAttribute("totalPage",totalPage);
        if (totalPage>0){
            List<Integer> pageNumber= IntStream.rangeClosed(1,totalPage).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumber",pageNumber);
        }
        return "admin/region/index";
    }


    @GetMapping(value = {"/add"})
    public String addCountry(Model model){
        model.addAttribute("regionRequest",new RegionRequest());
        model.addAttribute("savePath","/admin/region/add");
        model.addAttribute("countries",regionService.getCountries());
        model.addAttribute("result",new Result(false,""));
        return  "admin/region/add";
    }

    @PostMapping(value = {"/add"})
    public String addCountry(@Valid RegionRequest regionRequest, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("countries",regionService.getCountries());
            model.addAttribute("savePath","/admin/region/add");
            model.addAttribute("result",new Result(false,""));
            return "admin/region/add";
        }

        Result result=regionService.add(regionRequest);
        model.addAttribute("savePath","/admin/region/add");
        model.addAttribute("result",new Result(true,result.getMessage()));
        return "admin/region/add";
    }


    @GetMapping(value = {"/edit/{id}"})
    public String editCountry(@PathVariable(value = "id") Long id, Model model){
        model.addAttribute("savePath","/admin/region/edit/"+id);
        Optional<Region> regionOptional=regionService.getRegion(id);
        if (!regionOptional.isPresent()){
            return "redirect:/admin/region";
        }
        RegionRequest regionRequest=new  RegionRequest(regionOptional.get().getName(),
                regionOptional.get().getDescription(),regionOptional.get().getCountry().getId());
        model.addAttribute("regionRequest", regionRequest);
        model.addAttribute("countries",regionService.getCountries());

        model.addAttribute("result",new Result(false,""));
        return "admin/region/edit";
    }

    @PostMapping(value = {"/edit/{id}"})
    public String editCountry(@PathVariable("id") Long id,
                              @Valid RegionRequest regionRequest,
                              BindingResult bindingResult,Model model  ){
        if (bindingResult.hasErrors()){
            model.addAttribute("countries",regionService.getCountries());
            model.addAttribute("savePath","/admin/region/edit/"+id);
            model.addAttribute("result",new Result(false,""));
            return "admin/region/edit";
        }
        Result result= regionService.editRegion(id,regionRequest) ;
        model.addAttribute("countries",regionService.getCountries());
        model.addAttribute("savePath","/admin/region/edit/"+id);
        model.addAttribute("regionRequest",new RegionRequest());
        model.addAttribute("result",new Result(true,result.getMessage()));
        return "admin/region/edit";
    }


    @GetMapping(value = {"/delete/{id}"})
    public String deleteCountry(@PathVariable(value = "id") Long id,Model model){

        RegionRequest regionRequest=new RegionRequest(
                regionService.getRegion(id).get().getName(),
                regionService.getRegion(id).get().getDescription(),
                regionService.getRegion(id).get().getCountry().getId() );
        model.addAttribute("regionRequest",regionRequest);
        model.addAttribute("savePath","/admin/region/delete/"+id);
        model.addAttribute("countries",regionService.getCountries());
        model.addAttribute("result",new Result(false,""));
        return "admin/region/delete";
    }

    @PostMapping(value = {"/delete/{id}"})
    public String deleteCountryA(@PathVariable("id") Long id, Model model){

        model.addAttribute("regionRequest", new RegionRequest());
        model.addAttribute("savePath","/admin/region/delete/"+id);
        model.addAttribute("countries",regionService.getCountries());
        Result result=regionService.delete(id);
        model.addAttribute("result",new Result(true,result.getMessage()));
        return "admin/region/delete";
    }



}
