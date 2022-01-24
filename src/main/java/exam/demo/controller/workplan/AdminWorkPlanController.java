package exam.demo.controller.workplan;
import exam.demo.entity.User;
import exam.demo.exception.StorageException;
import exam.demo.payload.Result;
import exam.demo.payload.workplan.PlanRequest;
import exam.demo.security.CurrentUser;
import exam.demo.service.workplan.WorkPlanService;
import exam.demo.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Controller()
@RequestMapping("/admin/workplan")
public class AdminWorkPlanController {

    @Autowired
    private WorkPlanService service;

    @GetMapping(value = {"", "/"})
    public String getIndex(Model model) {
        model.addAttribute("admin", "admin");
        return "workplan/index";
    }
/*

    @GetMapping(value = {"/plan"})
    public String planIndex(@CurrentUser User user, Model model) {
        model.addAttribute("planlar", service.getAllbyUser(user));
        System.out.println(service.getAllbyUser(user));
        return "workplan/plan/index";
    }

    @GetMapping(value = {"/plan/all"})
    public String planAll(Model model) {
        model.addAttribute("planlar", service.getAll());
        return "workplan/plan/all";
    }

    */
/*@GetMapping("/plan/all")
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
            Page<plan> planPage = service.getSearch(search);
            model.addAttribute("planPage", planPage);
            int totalPage = planPage.getTotalPages();
//            System.out.println(totalPage);
            model.addAttribute("totalPage", totalPage);
            if (totalPage > 0) {
                List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
                //    System.out.println(pageNumbers.toString());
                model.addAttribute("pageNumber", pageNumbers);
            }

            return "workplan/plan/all";

        }

        Page<plan> planPage = service.getDocs(page, size);
        model.addAttribute("planPage", planPage);
        int totalPage = planPage.getTotalPages();
        model.addAttribute("totalPage", totalPage);

        if (totalPage > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());

            model.addAttribute("pageNumber", pageNumbers);

        }


        return "workplan/plan/all";

    }
*//*


    @PostMapping(value = {"/upload"})
    public String fileUpload(@RequestParam("file") MultipartFile[] file, Model model) {

        for (MultipartFile f : file) {
            String filename = StringUtils.cleanPath(f.getOriginalFilename());
            System.out.println("file" + filename);

        }

        for (MultipartFile f : file) {
            String filename = StringUtils.cleanPath(f.getOriginalFilename());

            try {
                if (f.isEmpty()) {
                    throw new StorageException("Failed to store empty file " + filename);
                }
                if (filename.contains("..")) {
                    // This is a security check
                    throw new StorageException(
                            "Cannot store file with relative path outside current directory "
                                    + filename);
                }
                try (InputStream inputStream = f.getInputStream()) {
                    Files.copy(inputStream, AppConstants.plan.resolve(filename),
                            StandardCopyOption.REPLACE_EXISTING);
                }
            } catch (IOException e) {
                throw new StorageException("Failed to store file " + filename, e);
            }


        }


        // return success response


        return "workplan/index";

    }

    @GetMapping(value = {"/plan/add"})
    public String add(Model model) {
        model.addAttribute("users", service.getAllUsers());
        model.addAttribute("savePath", "/workplan/plan/add");
        model.addAttribute("planRequest", new PlanRequest());
        model.addAttribute("result", new Result(false, ""));
        return "workplan/plan/add";
    }

    @PostMapping(value = {"/plan/add"})
    public String add(@Valid @ModelAttribute("planRequest") PlanRequest planRequest,
                      BindingResult bindingResult, @CurrentUser User user, Model model) {
        if (bindingResult.hasErrors()) {
            // System.out.println(planRequest.getFiles().length);
            for (MultipartFile f : planRequest.getFiles()) {
                String filename = StringUtils.cleanPath(f.getOriginalFilename());
                System.out.println("file--" + filename);

            }
            //System.out.println(TimeGenerator.getCurrentDatetime());
            // System.out.println(planRequest.getOutDate());

            model.addAttribute("users", service.getAllUsers());
            model.addAttribute("savePath", "/workplan/plan/add");
            model.addAttribute("result", new Result(false, ""));
            return "workplan/plan/add";
        }

        Result result = service.add(planRequest, user);
        model.addAttribute("users", service.getAllUsers());
        model.addAttribute("savePath", "/workplan/plan/add");
        model.addAttribute("result", new Result(true, result.getMessage()));
        return "workplan/plan/add";
    }

*/

    /*public String edit() {

    }

    public String delete() {

    }
*/


}
