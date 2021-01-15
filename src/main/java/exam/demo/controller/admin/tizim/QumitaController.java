package exam.demo.controller.admin.tizim;

import exam.demo.entity.tizim.Lavozim;
import exam.demo.entity.tizim.Qumita;
import exam.demo.payload.Result;
import exam.demo.payload.admin.tizim.LavozimRequest;
import exam.demo.payload.admin.tizim.QumitaRequest;
import exam.demo.service.tizim.QumitaService;
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
@RequestMapping("/admin/qumita")
public class QumitaController {

    @Autowired
    private QumitaService qumitaService;
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
            Page<Qumita> qumitaPage= qumitaService.search(search);
            model.addAttribute("qumitaPage",qumitaPage);
            int totalPage=qumitaPage.getTotalPages();

            model.addAttribute("totalPage",totalPage);
            if (totalPage>0){
                List<Integer> pageNumber= IntStream.rangeClosed(1,totalPage).boxed().collect(Collectors.toList());
                System.out.println(pageNumber);
                model.addAttribute("pageNumber",pageNumber);
            }

            return "admin/qumita/index";
        }

        Page<Qumita> qumitaPage= qumitaService.getAll(page,size);
        model.addAttribute("qumitaPage",qumitaPage);
        int totalPage=qumitaPage.getTotalPages();
        model.addAttribute("totalPage",totalPage);
        if (totalPage>0){
            List<Integer> pageNumber= IntStream.rangeClosed(1,totalPage).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumber",pageNumber);
        }
        return "admin/qumita/index";
    }

    @GetMapping(value = {"/add"})
    public String addQumita(Model model){
                     model.addAttribute("qumitaRequest",new QumitaRequest());
                     model.addAttribute("savePath","/admin/qumita/add");
                     model.addAttribute("result",new Result(false,""));
        return  "admin/qumita/add";
    }

    @PostMapping(value = {"/add"})
    public String addQumita(@Valid QumitaRequest qumitaRequest, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){

            model.addAttribute("savePath","/admin/qumita/add");
            model.addAttribute("result",new Result(false,""));
            return "admin/qumita/add";
        }

        Result result= qumitaService.add(qumitaRequest);
        model.addAttribute("savePath","/admin/qumita/add");
        model.addAttribute("result",new Result(true,result.getMessage()));
        return "admin/qumita/add";
    }


    @GetMapping(value = {"/edit/{id}"})
    public String editQumita(@PathVariable(value = "id") Long id,Model model){
        model.addAttribute("savePath","/admin/qumita/edit/"+id);
        Optional<Qumita> qumitaOptional= qumitaService.getQumita(id);
        if (!qumitaOptional.isPresent()){
            return "redirect:/admin/qumita";
        }
        QumitaRequest qumitaRequest=new  QumitaRequest(qumitaOptional.get().getName());
        model.addAttribute("qumitaRequest", qumitaRequest);
        model.addAttribute("result",new Result(false,""));
        return "admin/qumita/edit";
    }

    @PostMapping(value = {"/edit/{id}"})
    public String editQumita(@PathVariable("id") Long id,
                              @Valid QumitaRequest qumitaRequest,
                              BindingResult bindingResult,Model model  ){
        if (bindingResult.hasErrors()){
            model.addAttribute("savePath","/admin/qumita/edit/"+id);
            model.addAttribute("result",new Result(false,""));
            return "admin/qumita/edit";
        }
       Result result= qumitaService.editQumita(id,qumitaRequest) ;
        model.addAttribute("savePath","/admin/qumita/edit/"+id);
        model.addAttribute("qumitaRequest",new QumitaRequest());
        model.addAttribute("result",new Result(true,result.getMessage()));
        return "admin/qumita/edit";
    }


    @GetMapping(value = {"/delete/{id}"})
    public String deleteQumita(@PathVariable(value = "id") Long id,Model model){
        model.addAttribute("qumitaRequest", qumitaService.getQumita(id));
        model.addAttribute("savePath","/admin/qumita/delete/"+id);
        model.addAttribute("result",new Result(false,""));
        return "admin/qumita/delete";
    }

    @PostMapping(value = {"/delete/{id}"})
    public String deleteQumitaA(@PathVariable("id") Long id, Model model){

        model.addAttribute("qumitaRequest", new QumitaRequest());
        model.addAttribute("savePath","/admin/qumita/delete/"+id);
        Result result= qumitaService.delete(id);
        model.addAttribute("result",new Result(true,result.getMessage()));
        return "admin/qumita/delete";
    }
}


