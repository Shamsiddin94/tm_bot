package exam.demo.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import exam.demo.bot.BotService;
import exam.demo.entity.User;
import exam.demo.payload.AttachmentRequest;
import exam.demo.payload.Result;
import exam.demo.payload.ResultModel;
import exam.demo.security.CurrentUser;
import exam.demo.service.TelegramService;
import exam.demo.service.workplan.WorkPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = {"/telegram"})
public class TelegramController {

    @Autowired
    private TelegramService telegramService;
    @Autowired
    private WorkPlanService service;
    @Autowired
    private BotService botService;


    @PreAuthorize("hasRole('ROLE_FILESEND')")
    @GetMapping(value = {"/send"})
    public String sendIndex(@CurrentUser User user, Model model) {
        model.addAttribute("documents", telegramService.getAllSendDocs(user));
        model.addAttribute( new Result(false , ""));
     return  "telegram/send/index";
    }
    @PreAuthorize("hasRole('ROLE_FILESEND')")
    @GetMapping(value = {"/send/file"})
    public String sendFile(@CurrentUser User user, Model model) {

        model.addAttribute("attachmentRequest", new AttachmentRequest());
        model.addAttribute("savpath","/telegram/send/file");
        model.addAttribute("result",new Result(false, ""));
        return  "telegram/send/file";

    }
    @PreAuthorize("hasRole('ROLE_FILESEND')")
    @PostMapping(value = {"/send/file"})
    public String sendFile( @Valid @ModelAttribute("attachmentRequest") AttachmentRequest request, BindingResult bindingResult,
                            @CurrentUser User user, Model model) throws FileNotFoundException {
        System.out.println("test for request"+request.toString());
        Result result=new Result(false , "");
        if (bindingResult.hasErrors()){
            model.addAttribute("result",result);
            return  "telegram/send/file";
        } else if (botService.checkClient().getSuccess()) {
            result.setMessage(botService.checkClient().getMessage());
            result.setSuccess(true);
            model.addAttribute("savpath","/telegram/send/file");
             model.addAttribute("result",result);
            return  "telegram/send/file";
        }else if (botService.saveSendFile(request,user).getSuccess()) {
            result.setMessage("Fayl yuborilmadi qayta urinib ko'ring");
            result.setSuccess(true);
            model.addAttribute("savpath", "/telegram/send/file");
            model.addAttribute("result", result);
            return "telegram/send/file";
        }
        System.out.println(request.toString());
        model.addAttribute("attachmentRequest", new AttachmentRequest());
        result.setSuccess(true);
        result.setType(true);
        result.setMessage("Hujjatingiz yuborildi");
        model.addAttribute("result",result);

        return  "telegram/send/file";
    }

    @PreAuthorize("hasRole('ROLE_FILESEND')")
    @GetMapping(value = {"/send/get/{id}"})
    @ResponseBody
    public ResponseEntity<Resource> getDocSend(@PathVariable("id") Long id, @CurrentUser User user) {
        ResultModel resultModel = telegramService.getSendDoc(id, user);
        System.out.println(resultModel.toString());
        if (resultModel.getSuccess()) {

            Resource file = (Resource) resultModel.getObject();
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(resultModel.getData().get("type")))
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + URLEncoder.encode(resultModel.getMessage())).body(file);
        }
        Resource file = (Resource) resultModel.getObject();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + URLEncoder.encode(resultModel.getMessage()))
                .contentType(!resultModel.getData().get("type").isEmpty() ? MediaType.parseMediaType(resultModel.getData().get("type")) : MediaType.ALL)
                .body(file);
    }


    @PreAuthorize("hasRole('ROLE_FILESEND')")
    @GetMapping(value = {"/send/delete/{id}"})

    public String  sendDelete(@PathVariable("id") Long id, @CurrentUser User user,Model model) {
        telegramService.deleteSendAttachment(user, id);
        model.addAttribute("documents", telegramService.getAllSendDocs(user));
        model.addAttribute( new Result(false , ""));
        return  "telegram/send/index";
    }


    @GetMapping(value = {"/document/delete/{id}"})
    public String documentDelete(@PathVariable("id") Long id, @CurrentUser User user, Model model) {


        telegramService.deleteAttachment(user, id);
        model.addAttribute("documents", telegramService.getAllDocs(user));
        return "telegram/document";
    }

    @GetMapping(value = {"/picture/delete/{id}"})
    public String pictureDelete(@PathVariable("id") Long id, @CurrentUser User user, Model model) {

        telegramService.deleteAttachment(user, id);

        model.addAttribute("pictures", telegramService.allPictures(user));
        return "telegram/picture";
    }


    @GetMapping(value = {"/message"})
    public String messagePage(@CurrentUser User user, Model model) {

        model.addAttribute("messages", telegramService.allMessage(user));

        return "telegram/message";
    }

    @GetMapping(value = {"/message/delete/{id}"})
    public String messageDelete(@PathVariable("id") Long id, @CurrentUser User user, Model model) {

        telegramService.deleteMessage(user, id);
        model.addAttribute("messages", telegramService.allMessage(user));
        return "telegram/message";
    }

    @GetMapping(value = {"/picture"})
    public String indexPicture(@CurrentUser User user, Model model) {

        model.addAttribute("pictures", telegramService.allPictures(user));
        return "telegram/picture";
    }

    @GetMapping(value = {"/document"})
    public String indexDocument(@CurrentUser User user, Model model) {
        model.addAttribute("documents", telegramService.getAllDocs(user));
        return "telegram/document";
    }

    @GetMapping(value = {"/picture/get/{id}"})
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable("id") Long id, @CurrentUser User user) {
        ResultModel resultModel = telegramService.getPicture(id, user);
        System.out.println(resultModel.toString());
        if (resultModel.getSuccess()) {

            Resource file = (Resource) resultModel.getObject();
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(resultModel.getData().get("type")))
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + URLEncoder.encode(resultModel.getMessage())).body(file);
        }
        Resource file = (Resource) resultModel.getObject();
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + URLEncoder.encode(resultModel.getMessage())).body(file);
    }

    @GetMapping(value = {"/document/get/{id}"})
    @ResponseBody
    public ResponseEntity<Resource> getDoc(@PathVariable("id") Long id, @CurrentUser User user) {
        ResultModel resultModel = telegramService.getDoc(id, user);
        System.out.println(resultModel.toString());
        if (resultModel.getSuccess()) {

            Resource file = (Resource) resultModel.getObject();
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(resultModel.getData().get("type")))
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + URLEncoder.encode(resultModel.getMessage())).body(file);
        }
        Resource file = (Resource) resultModel.getObject();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + URLEncoder.encode(resultModel.getMessage()))
                .contentType(!resultModel.getData().get("type").isEmpty() ? MediaType.parseMediaType(resultModel.getData().get("type")) : MediaType.ALL)
                .body(file);
    }

    @JsonIgnoreProperties
    @GetMapping(value = "/getJson")
    @ResponseBody
    public Map<String, String> getData(@CurrentUser User user) {
        Map<String, String> data = new HashMap<>();
        int rasm = telegramService.allPictures(user).stream()
                .filter(attachment -> {
                    return attachment.isOpen() == false;
                }).collect(Collectors.toList()).size();
        int hujjat = telegramService.getAllDocs(user).stream()
                .filter(attachment -> {
                    return attachment.isOpen() == false;
                }).collect(Collectors.toList()).size();
        int allDoc = telegramService.getAllDocs(user).size();
        int allImg = telegramService.allPictures(user).size();
        data.put("tJami", String.valueOf(rasm + hujjat));
        data.put("tRasm", String.valueOf(rasm));
        data.put("tHujjat", String.valueOf(hujjat));
        data.put("allDoc", String.valueOf(allDoc));
        data.put("allImg", String.valueOf(allImg));
        return data;
    }

    @JsonIgnoreProperties
    @GetMapping(value = "/get/{ms}")
    @ResponseBody
    public String get(@PathVariable("ms") String string, @CurrentUser User user) {
        Map<String, String> data = new HashMap<>();
        int rasm = telegramService.allPictures(user).size();
        int hujjat = telegramService.getAllDocs(user).size();

        data.put("tJami", String.valueOf(rasm + hujjat));

        System.out.println(string);
        return string;
    }


}
