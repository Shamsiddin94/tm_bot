package exam.demo.service;

import exam.demo.entity.User;
import exam.demo.entity.bot.Attachment;
import exam.demo.entity.bot.Client;
import exam.demo.entity.enums.FileType;
import exam.demo.exception.StorageFileNotFoundException;
import exam.demo.payload.Result;
import exam.demo.payload.ResultModel;
import exam.demo.payload.admin.ClientRequest;
import exam.demo.repository.bot.AttachmentRepository;
import exam.demo.repository.bot.ClientRepository;
import exam.demo.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import org.springframework.core.io.Resource;

import javax.activation.MimetypesFileTypeMap;
import java.awt.*;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.*;
import java.util.List;

@Service
public class TelegramService {
    @Autowired
    private AttachmentRepository attachmentRepository;
    @Autowired
    private ClientRepository clientRepository;
@Autowired
private UserService userService;
    public List<Attachment> allPictures(User user){
        List<Client>  clients=clientRepository.findByUser(user);
        List<Attachment> attachments=new ArrayList<>();
        clients.forEach(client -> {
            attachments.addAll(attachmentRepository.findByClientAndType(client,FileType.PICTURE));
        });
        return attachments;
    }

    public ResultModel getPicture(Long id, User user){
        List<Attachment> attachments=allPictures(user);
        ResultModel resultModel=new ResultModel();
       Optional< Attachment>  attachmentOptional=attachmentRepository.findById(id);
        Map<String,String> map=new HashMap<>();
       if (attachmentOptional.isPresent()){
       Attachment attachment=attachmentOptional.get();
       attachment.setOpen(true);
       attachmentRepository.save(attachment);
           if (attachments.contains(attachment)){
               map.put("type",attachment.getContentType());
               Path file= AppConstants.botFiles.resolve(attachment.getFileUrl());
               try {
                   Resource resource=new UrlResource(file.toUri());
                   if (resource.exists() || resource.isReadable()) {
                       resultModel.setSuccess(true);
                       resultModel.setData(map);
                       resultModel.setMessage(attachment.getFileUrl());
                       resultModel.setObject(resource);
                       return resultModel;
                   }
                   else {
                       throw new StorageFileNotFoundException(
                               "Could not read file: " + attachment.getFileUrl());

                   }
               } catch (MalformedURLException e) {
                   e.printStackTrace();
               }



               resultModel.setSuccess(false);
               resultModel.setData(map);
               resultModel.setMessage(attachment.getFileUrl());
               return resultModel;
           }
       }
        resultModel.setSuccess(false);
        resultModel.setMessage("");
        return resultModel;
    }

    public  List<Attachment> getAllDocs(User user){
        List<Client> clients=clientRepository.findByUser(user);
        List<Attachment> attachments=new ArrayList<>();
        clients.forEach(client -> {
            attachments.addAll(attachmentRepository.findByClientAndType(client,FileType.DOCUMENT));
        });
         return attachments;
    }

    public  ResultModel getDoc(Long id,User user){
        List<Attachment> attachments=getAllDocs(user);
        ResultModel resultModel=new ResultModel();
        Map<String,String> map=new HashMap<>();
        Optional< Attachment>  attachmentOptional=attachmentRepository.findById(id);
        if (attachmentOptional.isPresent()){
            Attachment attachment=attachmentOptional.get();
            attachment.setOpen(true);
            attachmentRepository.save(attachment);
            if (attachments.contains(attachment)){
                Path file= AppConstants.botFiles.resolve(attachment.getFileUrl());
                try {
                    Resource resource=new UrlResource(file.toUri());
                    if (resource.exists() || resource.isReadable()) {
                        map.put("type",attachment.getContentType());
                        map.put("size",attachment.getSize());
                        resultModel.setSuccess(true);
                        resultModel.setData(map);

                        resultModel.setMessage(attachment.getFileName());
                        resultModel.setObject(resource);
                        return resultModel;
                    }
                    else {
                        throw new StorageFileNotFoundException(
                                "Could not read file: " + attachment.getFileUrl());

                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }



                resultModel.setSuccess(false);
                resultModel.setMessage(attachment.getFileName());
                return resultModel;
            }
        }
        resultModel.setSuccess(false);
        resultModel.setMessage("");
        return resultModel;
    }

    public Result addClient(ClientRequest clientRequest){
       if (clientRepository.findByChatId(clientRequest.getChatId()).isPresent()){
           return new Result(true,"Ushbu chat oldin kiritilgan");
       }

        Client client=new Client();
        client.setUserName(clientRequest.getUserName());
        client.setLastName(clientRequest.getLastName());
        client.setChatId(clientRequest.getChatId());
        client.setFirstName(clientRequest.getFirstName());
        client.setUser(userService.getUser(clientRequest.getUserId()));
        clientRepository.save(client);
        return new Result(true,"Client saqlandi");
    }

    public Result editClient(Long id, ClientRequest clientRequest) {
        Optional<Client> clientOpt=clientOpt(id);
        if (clientOpt.isPresent()) {
            Client client = clientOpt.get();
            client.setUserName(clientRequest.getUserName());
            client.setLastName(clientRequest.getLastName());
            client.setChatId(clientRequest.getChatId());
            client.setFirstName(clientRequest.getFirstName());
            client.setUser(userService.getUser(clientRequest.getUserId()));
            clientRepository.save(client);
            return new Result(true,"Client o'zgartirildi");
        }
        return new Result(true,"Client topilmadi");

    }

    public List<Client> allClients(){
        return  clientRepository.findAll();
    }

public Optional<Client> clientOpt(Long id){
        return clientRepository.findById(id);
}

}
