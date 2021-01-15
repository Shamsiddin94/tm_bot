package exam.demo.bot;

import com.google.gson.Gson;
import exam.demo.entity.bot.Attachment;
import exam.demo.entity.bot.Client;
import exam.demo.entity.enums.FileType;
import exam.demo.payload.Result;
import exam.demo.payload.ResultModel;
import exam.demo.repository.bot.AttachmentRepository;
import exam.demo.repository.bot.ClientRepository;
import jdk.nashorn.internal.parser.JSONParser;
import net.bytebuddy.asm.Advice;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static exam.demo.bot.TelegramBot.botToken;
import static exam.demo.bot.TelegramBot.upPath;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

@Service
public class BotService {

     @Autowired
     private ClientRepository clientRepository;
     @Autowired
     private AttachmentRepository attachmentRepository;
     private Client client;
     @Transactional()
     public ResultModel existUser(Long chatId){
          ResultModel result=new ResultModel();
       Optional<Client>  clientOptional=clientRepository.findByChatId(chatId);
       if (!clientOptional.isPresent()){
            result.setMessage("User topilmadi");
            result.setSuccess(false);
            return result;
       }
          result.setMessage("User topildi");
          result.setSuccess(true);
          client=clientOptional.get();
          result.setObject(client);
          return result;
     }

     public Result pictureUpload(List<PhotoSize> photoSizes) {
          Result result = new Result();
          int size=photoSizes.size();
         System.out.println(photoSizes.toString());
         System.out.println(size-1);
          PhotoSize photoSize=photoSizes.get(size-1);
          System.out.println(photoSize);

          String name= UUID.randomUUID().toString();
          Attachment attachment=new Attachment();

          try {
              result= uploadFile( photoSize.getFileId(),name);
              attachment.setFileUrl(result.getMessage());
              attachment.setFileName(photoSize.getFileUniqueId());
              attachment.setClient(client);
              attachment.setType(FileType.PICTURE);
              attachment.setSize(String.valueOf(photoSize.getFileSize()));
              attachment.setCreatedBy(client.getUser().getId());
              attachment.setUpdatedBy(client.getUser().getId());

              //patching mistake from the above broken code.
              System.out.println(getContentType(result.getMessage()));
              attachment.setContentType(getContentType(result.getMessage()));
              attachmentRepository.save(attachment);

          } catch (IOException e)
          {
               e.printStackTrace();
               result.setSuccess(false);
               result.setMessage("Fayl yuklashda xatolik sodir bo'ldi\n " +
                       "Fayl   5 mb rasm yoki 20 mb document bo'lishi zarur \n"+
                       "Iltimos administratorga murojaat qiling");
               return result;
          }
          result.setSuccess(true);
          return result;
     }


     public Result documentUpload(Document document){
          Result result=new Result();
         String name=UUID.randomUUID().toString() ;
         Attachment attachment=new Attachment();
         System.out.println(document);
          try {

              result= uploadFile(document.getFileId(),name);
              attachment.setFileUrl(result.getMessage());
              attachment.setFileName(document.getFileName());
              attachment.setClient(client);
              attachment.setType(FileType.DOCUMENT);
              attachment.setSize(String.valueOf(document.getFileSize()));
              attachment.setUpdatedBy(client.getId());
              attachment.setCreatedBy(client.getId());
              attachment.setContentType(getContentType(result.getMessage()));
              attachmentRepository.save(attachment);

          } catch (IOException e)
          {
              e.printStackTrace();
              result.setSuccess(false);
              result.setMessage("Fayl yuklashda xatolik sodir bo'ldi\n " +
                      "Iltimos administratorga murojaat qiling");
              return result;

          }
          result.setSuccess(true);
          return result;
     }

     public Result uploadFile(String file_id,String file_name) throws IOException {

        URL url = new URL("https://api.telegram.org/bot" + botToken + "/getFile?file_id=" + file_id);
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String res = in.readLine();
        JSONObject jresult = new JSONObject(res);
        System.out.println(jresult);
        JSONObject key= (JSONObject) jresult.get("result");
        String name=key.getString("file_path");
        //System.out.println(name);
        List<String> dt= Arrays.asList(name.split("/"));
        file_name=file_name+dt.get(1);
        JSONObject path = jresult.getJSONObject("result");
        String file_path = path.getString("file_path");
        URL downoload = new URL("https://api.telegram.org/file/bot" + botToken + "/" + file_path);
        FileOutputStream fos = new FileOutputStream(upPath+"/" + file_name);
        System.out.println("Yuklash boshlandi");
        ReadableByteChannel rbc = Channels.newChannel(downoload.openStream());
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        rbc.close();
        System.out.println("Yuklash yakunlandi");
        return new Result(true,file_name);
    }

    public String getContentType(String path){
        MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();

        String contentType = mimeTypesMap.getContentType(path);
        System.out.println(contentType);

        //patching mistake from the above broken code.
        if (path.endsWith("png")) {
            contentType = "image/png";
        } else if (path.endsWith("html")) {
            contentType = "text/html";
        } else if (path.endsWith("jpg")) {
            contentType = "image/jpeg";
        }
        else if (path.endsWith("pdf")) {
            contentType = "application/pdf";
        } else if (path.endsWith("doc")) {
            contentType = "application/msword";
        } else if (path.endsWith("docx")) {
            contentType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        }else if (path.endsWith("xls")) {
            contentType = "application/vnd.ms-excel";
        }else if (path.endsWith("xlsx")) {
            contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        }else if (path.endsWith("ppt")) {
            contentType = "application/vnd.ms-powerpoint";
        }else if (path.endsWith("pptx")) {
            contentType = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
        }
        return contentType;
    }

}
