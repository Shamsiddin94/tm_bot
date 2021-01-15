package exam.demo.controller.admin;

import exam.demo.entity.District;
import exam.demo.entity.Region;
import exam.demo.payload.admin.DistrictRequest;
import exam.demo.payload.Result;
import exam.demo.service.admin.DistrictService;
import exam.demo.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/admin/district")
public class DistrictController {
    @Autowired
    private DistrictService districtService;

    @GetMapping(value = {"", "/"})
    public String index(Model model, @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
                        @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                        @RequestParam(value = "search", defaultValue = "") String search) {

        if (page < 0) {
            page = 0;
        }
        if (size > AppConstants.MAX_PAGE_SIZE) {
            size = AppConstants.MAX_PAGE_SIZE;
        }
        if (!search.isEmpty()) {
            Page<District> districtPage = districtService.search(search);
            model.addAttribute("districtPage", districtPage);
            int totalPage = districtPage.getTotalPages();

            model.addAttribute("totalPage", totalPage);
            if (totalPage > 0) {
                List<Integer> pageNumber = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
                System.out.println(pageNumber);
                model.addAttribute("pageNumber", pageNumber);
            }

            return "admin/district/index";
        }

        Page<District> districtPage = districtService.getDistricts(page, size);
        model.addAttribute("districtPage", districtPage);
        int totalPage = districtPage.getTotalPages();
        model.addAttribute("totalPage", totalPage);
        if (totalPage > 0) {
            List<Integer> pageNumber = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumber", pageNumber);
        }
        return "admin/district/index";
    }

    @GetMapping(value = {"/add"})
    public String addDistrict(Model model) {
        model.addAttribute("districtRequest", new DistrictRequest());
        model.addAttribute("savePath", "/admin/district/add");
      //  model.addAttribute("regions",districtService.getRegions());
        model.addAttribute("countries",districtService.getCountries());
        model.addAttribute("result", new Result(false, ""));
        return "admin/district/add";
    }

    @PostMapping(value = {"/add"})
    public String addDistrict(@Valid DistrictRequest districtRequest, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
          //  model.addAttribute("regions",districtService.getRegions());
            model.addAttribute("countries",districtService.getCountries());
            model.addAttribute("savePath", "/admin/district/add");
            model.addAttribute("result", new Result(false, ""));
            return "admin/district/add";
        }
        model.addAttribute("regions",districtService.getRegions());
        model.addAttribute("countries",districtService.getCountries());
        Result result = districtService.add(districtRequest);
        model.addAttribute("savePath", "/admin/district/add");
        model.addAttribute("result", new Result(true, result.getMessage()));
        model.addAttribute("districtRequest", new DistrictRequest());
        return "admin/district/add";
    }


    @GetMapping(value = {"/edit/{id}"})
    public String editDistrict(@PathVariable(value = "id") Long id, Model model) {
        model.addAttribute("savePath", "/admin/district/edit/" + id);
        Optional<District> districtOptional = districtService.getDistrict(id);
        if (!districtOptional.isPresent()) {
            return "redirect:/admin/district";
        }
        DistrictRequest districtRequest = new DistrictRequest(districtOptional.get().getName(),
                districtOptional.get().getDescription(),districtOptional.get().getRegion().getId(),
                districtOptional.get().getRegion().getCountry().getId());
        model.addAttribute("regions",districtService.getRegions());
        model.addAttribute("countries",districtService.getCountries());
        model.addAttribute("districtRequest", districtRequest);
        model.addAttribute("result", new Result(false, ""));
        return "admin/district/edit";
    }

    @PostMapping(value = {"/edit/{id}"})
    public String editDistrict(@PathVariable("id") Long id,
                              @Valid DistrictRequest districtRequest,
                              BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("regions",districtService.getRegions());
            model.addAttribute("countries",districtService.getCountries());
            model.addAttribute("savePath", "/admin/district/edit/" + id);
            model.addAttribute("result", new Result(false, ""));
            return "admin/district/edit";
        }
       model.addAttribute("regions",districtService.getRegions());
        model.addAttribute("countries",districtService.getCountries());
        Result result = districtService.editDistrict(id, districtRequest);
        model.addAttribute("savePath", "/admin/district/edit/" + id);
        model.addAttribute("districtRequest", new DistrictRequest());
        model.addAttribute("result", new Result(true, result.getMessage()));
        return "admin/district/edit";
    }


    @GetMapping(value = {"/delete/{id}"})
    public String deleteDistrict(@PathVariable(value = "id") Long id, Model model) {
        DistrictRequest districtRequest=new DistrictRequest(
                districtService.getDistrict(id).get().getName(),
                districtService.getDistrict(id).get().getDescription(),
                districtService.getDistrict(id).get().getRegion().getId(),
                districtService.getDistrict(id).get().getRegion().getCountry().getId()
        );
        model.addAttribute("districtRequest", districtRequest);
        model.addAttribute("savePath", "/admin/district/delete/" + id);
        model.addAttribute("result", new Result(false, ""));
        model.addAttribute("regions",districtService.getRegions());
        model.addAttribute("countries",districtService.getCountries());
        return "admin/district/delete";
    }

    @PostMapping(value = {"/delete/{id}"})
    public String deleteDistrictA(@PathVariable("id") Long id, Model model) {

        model.addAttribute("districtRequest", new DistrictRequest());
        model.addAttribute("savePath", "/admin/district/delete/" + id);
        Result result = districtService.delete(id);
        model.addAttribute("regions",districtService.getRegions());
        model.addAttribute("countries",districtService.getCountries());
        model.addAttribute("result", new Result(true, result.getMessage()));
        return "admin/district/delete";
    }

    @GetMapping(value = {"/getRegions"})
    @ResponseBody
    public List<Region> getRegions(@RequestParam("countryId") Long id){
        List<Region>  list= districtService.getRegionByCountry(id);
       if (!list.isEmpty()){
           return list;
       }
       List<Region> regionList=new ArrayList<>();
        return  regionList;
    }
}
