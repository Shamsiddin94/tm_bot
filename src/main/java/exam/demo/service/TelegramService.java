package exam.demo.service;

import exam.demo.entity.User;
import exam.demo.entity.bot.Attachment;
import exam.demo.entity.bot.Client;
import exam.demo.entity.bot.Message;
import exam.demo.entity.enums.FileType;
import exam.demo.exception.StorageFileNotFoundException;
import exam.demo.payload.Result;
import exam.demo.payload.ResultModel;
import exam.demo.payload.admin.ClientRequest;
import exam.demo.repository.bot.AttachmentRepository;
import exam.demo.repository.bot.ClientRepository;
import exam.demo.repository.bot.MessageRepository;
import exam.demo.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import org.springframework.core.io.Resource;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class TelegramService {
    @Autowired
    private AttachmentRepository attachmentRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private UserService userService;





    public List<Message> allMessage(User user) {
        List<Client> clients = clientRepository.findByUser(user);
        List<Message> messages = new ArrayList<>();
        clients.forEach(client -> {
            messages.addAll(messageRepository.findByClientAndDelete(client,false));
        });
        return messages;
    }

    public void deleteMessage(User user,Long id){

        List<Client> clients = clientRepository.findByUser(user);
        List<Message> messages = new ArrayList<>();
        clients.forEach(client -> {
            messages.addAll(messageRepository.findByClientAndDelete(client,false));
        });
        messages.forEach(ms->{
            if (Objects.equals(ms.getId(), id)){
                ms.setDelete(true);
                messageRepository.save(ms);

            }
        });

    }

    public void deleteAttachment(User user,Long id){

       List<Attachment> attachments=allPictures(user);
       attachments.addAll(getAllDocs(user));


       attachments.forEach(atm->{
            if (Objects.equals(atm.getId(), id)){
                atm.setDelete(true);
               attachmentRepository.save(atm);

            }
        });

    }



    //ReceiveFiles
    public List<Attachment> allPictures(User user) {
        List<Client> clients = clientRepository.findByUser(user);
        List<Attachment> attachments = new ArrayList<>();
        clients.forEach(client -> {
            attachments.addAll(attachmentRepository.findByClientAndTypeAndDelete(client, FileType.PICTURE,false));
        });
        return attachments;
    }

    public ResultModel getPicture(Long id, User user) {
        List<Attachment> attachments = allPictures(user);
        ResultModel resultModel = new ResultModel();
        Optional<Attachment> attachmentOptional = attachmentRepository.findById(id);
        Map<String, String> map = new HashMap<>();
        if (attachmentOptional.isPresent()) {
            Attachment attachment = attachmentOptional.get();
            attachment.setOpen(true);
            attachmentRepository.save(attachment);
            if (attachments.contains(attachment)) {
                map.put("type", attachment.getContentType());
                Path file = AppConstants.botFiles.resolve(attachment.getFileUrl());
                try {
                    Resource resource = new UrlResource(file.toUri());
                    if (resource.exists() || resource.isReadable()) {
                        resultModel.setSuccess(true);
                        resultModel.setData(map);
                        resultModel.setMessage(attachment.getFileUrl());
                        resultModel.setObject(resource);
                        return resultModel;
                    } else {
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

    public List<Attachment> getAllDocs(User user) {
        List<Client> clients = clientRepository.findByUser(user);
        List<Attachment> attachments = new ArrayList<>();
        clients.forEach(client -> {
            attachments.addAll(attachmentRepository.findByClientAndTypeAndDelete(client, FileType.DOCUMENT,false));
        });
        return attachments;
    }

    public ResultModel getDoc(Long id, User user) {
        List<Attachment> attachments = getAllDocs(user);
        ResultModel resultModel = new ResultModel();
        Map<String, String> map = new HashMap<>();
        Optional<Attachment> attachmentOptional = attachmentRepository.findById(id);
        if (attachmentOptional.isPresent()) {
            Attachment attachment = attachmentOptional.get();
            attachment.setOpen(true);
            attachmentRepository.save(attachment);
            if (attachments.contains(attachment)) {
                Path file = (attachment.getType().name().equals("SENDDOC"))?AppConstants.botFileSend.resolve(attachment.getFileUrl()):AppConstants.botFiles.resolve(attachment.getFileUrl());
                try {
                    Resource resource = new UrlResource(file.toUri());
                    if (resource.exists() || resource.isReadable()) {
                        map.put("type", attachment.getContentType());
                        map.put("size", attachment.getSize());
                        resultModel.setSuccess(true);
                        resultModel.setData(map);

                        resultModel.setMessage(attachment.getFileName());
                        resultModel.setObject(resource);
                        return resultModel;
                    } else {
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

    //SendFiles

    public List<Attachment> allSendPictures(User user) {
        List<Client> clients = clientRepository.findByUser(user);
        List<Attachment> attachments = new ArrayList<>();
        clients.forEach(client -> {
            attachments.addAll(attachmentRepository.findByClientAndTypeAndDelete(client, FileType.SENDPIC,false));
        });
        return attachments;
    }

    public ResultModel getSendPicture(Long id, User user) {
        List<Attachment> attachments = allSendPictures(user);
        ResultModel resultModel = new ResultModel();
        Optional<Attachment> attachmentOptional = attachmentRepository.findById(id);
        Map<String, String> map = new HashMap<>();
        if (attachmentOptional.isPresent()) {
            Attachment attachment = attachmentOptional.get();
            attachment.setOpen(true);
            attachmentRepository.save(attachment);
            if (attachments.contains(attachment)) {
                map.put("type", attachment.getContentType());
                Path file = AppConstants.botFiles.resolve(attachment.getFileUrl());
                try {
                    Resource resource = new UrlResource(file.toUri());
                    if (resource.exists() || resource.isReadable()) {
                        resultModel.setSuccess(true);
                        resultModel.setData(map);
                        resultModel.setMessage(attachment.getFileUrl());
                        resultModel.setObject(resource);
                        return resultModel;
                    } else {
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

    public List<Attachment> getAllSendDocs(User user) {
        List<Client> clients = clientRepository.findByUser(user);
        List<Attachment> attachments = new ArrayList<>();
        clients.forEach(client -> {
            attachments.addAll(attachmentRepository.findByClientAndTypeAndDelete(client, FileType.SENDDOC,false));
        });
        return attachments;
    }

    public ResultModel getSendDoc(Long id, User user) {
        List<Attachment> attachments = getAllSendDocs(user);
        ResultModel resultModel = new ResultModel();
        Map<String, String> map = new HashMap<>();
        Optional<Attachment> attachmentOptional = attachmentRepository.findById(id);
        if (attachmentOptional.isPresent()) {
            Attachment attachment = attachmentOptional.get();
            attachment.setOpen(true);
            attachmentRepository.save(attachment);
            if (attachments.contains(attachment)) {
                Path file = AppConstants.botFiles.resolve(attachment.getFileUrl());
                try {
                    Resource resource = new UrlResource(file.toUri());
                    if (resource.exists() || resource.isReadable()) {
                        map.put("type", attachment.getContentType());
                        map.put("size", attachment.getSize());
                        resultModel.setSuccess(true);
                        resultModel.setData(map);

                        resultModel.setMessage(attachment.getFileName());
                        resultModel.setObject(resource);
                        return resultModel;
                    } else {
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




    public Result addClient(ClientRequest clientRequest) {
        if (clientRepository.findByChatId(clientRequest.getChatId()).isPresent()) {
            return new Result(true, "Ushbu chat oldin kiritilgan");
        }

        Client client = new Client();
        client.setUserName(clientRequest.getUserName());
        client.setLastName(clientRequest.getLastName());
        client.setChatId(clientRequest.getChatId());
        client.setFirstName(clientRequest.getFirstName());
        client.setUser(userService.getUser(clientRequest.getUserId()));
        clientRepository.save(client);
        return new Result(true, "Client saqlandi");
    }

    public Result editClient(Long id, ClientRequest clientRequest) {
        Optional<Client> clientOpt = clientOpt(id);
        if (clientOpt.isPresent()) {
            Client client = clientOpt.get();
            client.setUserName(clientRequest.getUserName());
            client.setLastName(clientRequest.getLastName());
            client.setChatId(clientRequest.getChatId());
            client.setFirstName(clientRequest.getFirstName());
            client.setUser(userService.getUser(clientRequest.getUserId()));
            clientRepository.save(client);
            return new Result(true, "Client o'zgartirildi");
        }
        return new Result(true, "Client topilmadi");

    }

    public List<Client> allClients() {
        return clientRepository.findAll();
    }

    public Optional<Client> clientOpt(Long id) {
        return clientRepository.findById(id);
    }

}
