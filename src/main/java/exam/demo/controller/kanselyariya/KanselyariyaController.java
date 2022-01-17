package exam.demo.controller.kanselyariya;

import exam.demo.entity.User;
import exam.demo.exception.StorageException;
import exam.demo.payload.Result;
import exam.demo.payload.kanselyariya.HujjatRequest;
import exam.demo.security.CurrentUser;
import exam.demo.service.kanselyariya.HujjatService;
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

@Controller
@RequestMapping("/kanselyariya")
public class KanselyariyaController {
    @Autowired
    private HujjatService service;

    @GetMapping(value = {"", "/"})
    public String getIndex(Model model) {
        model.addAttribute("admin", "admin");
        return "kanselyariya/index";
    }

    @GetMapping(value = {"/hujjat"})
    public String hujjatIndex(@CurrentUser User user, Model model) {

        model.addAttribute("hujjatlar",service.getAllbyUser(user));
        System.out.println(service.getAllbyUser(user));
        return "kanselyariya/hujjat/index";
    }

   @GetMapping(value = {"/hujjat/all"})
    public String hujjatAll( Model model) {
        model.addAttribute("hujjatlar",service.getAll());
       return "kanselyariya/hujjat/all";
    }

    /*@GetMapping("/hujjat/all")
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
            Page<Hujjat> hujjatPage = service.getSearch(search);
            model.addAttribute("hujjatPage", hujjatPage);
            int totalPage = hujjatPage.getTotalPages();
//            System.out.println(totalPage);
            model.addAttribute("totalPage", totalPage);
            if (totalPage > 0) {
                List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
                //    System.out.println(pageNumbers.toString());
                model.addAttribute("pageNumber", pageNumbers);
            }

            return "kanselyariya/hujjat/all";

        }

        Page<Hujjat> hujjatPage = service.getDocs(page, size);
        model.addAttribute("hujjatPage", hujjatPage);
        int totalPage = hujjatPage.getTotalPages();
        model.addAttribute("totalPage", totalPage);

        if (totalPage > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());

            model.addAttribute("pageNumber", pageNumbers);

        }


        return "kanselyariya/hujjat/all";

    }
*/



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
                    Files.copy(inputStream, AppConstants.hujjat.resolve(filename),
                            StandardCopyOption.REPLACE_EXISTING);
                }
            } catch (IOException e) {
                throw new StorageException("Failed to store file " + filename, e);
            }


        }


        // return success response


        return "kanselyariya/index";

    }

    @GetMapping(value = {"/hujjat/add"})
    public String add(Model model) {
        model.addAttribute("users",service.getAllUsers());
            model.addAttribute("tasnif",service.getAllTasnif());
            model.addAttribute("tashkilot",service.getAllTashkilot());
        model.addAttribute("savePath","/kanselyariya/hujjat/add");
        model.addAttribute("hujjatRequest",new HujjatRequest());
        model.addAttribute("result", new Result(false, ""));
        return "kanselyariya/hujjat/add";
    }

    @PostMapping(value = {"/hujjat/add"})
    public String add(@Valid @ModelAttribute("hujjatRequest") HujjatRequest hujjatRequest,
                      BindingResult bindingResult,@CurrentUser User user, Model model) {
        if (bindingResult.hasErrors()){
           // System.out.println(hujjatRequest.getFiles().length);
            for (MultipartFile f : hujjatRequest.getFiles()) {
                String filename = StringUtils.cleanPath(f.getOriginalFilename());
                System.out.println("file--" + filename);

            }
            //System.out.println(TimeGenerator.getCurrentDatetime());
           // System.out.println(hujjatRequest.getOutDate());
            model.addAttribute("tasnif",service.getAllTasnif());
            model.addAttribute("tashkilot",service.getAllTashkilot());
            model.addAttribute("users",service.getAllUsers());
            model.addAttribute("savePath","/kanselyariya/hujjat/add");
            model.addAttribute("result", new Result(false, ""));
            return "kanselyariya/hujjat/add";
        }

        Result result=service.add(hujjatRequest,user);
        model.addAttribute("users",service.getAllUsers());
        model.addAttribute("savePath","/kanselyariya/hujjat/add");
        model.addAttribute("result", new Result(true, result.getMessage()));
        return "kanselyariya/hujjat/add";
    }


    /*public String edit() {

    }

    public String delete() {

    }
*/

}