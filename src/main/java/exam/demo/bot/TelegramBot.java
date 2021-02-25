package exam.demo.bot;

import exam.demo.entity.bot.Client;
import exam.demo.payload.Result;
import exam.demo.payload.ResultModel;
import exam.demo.utils.AppConstants;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URL;
import java.util.*;
@Component
public class TelegramBot extends TelegramLongPollingBot {
    private  String token = getBotToken();
    public static final String botToken="992636417:AAGKl06nmI37nRxGYqA0kcnTK5YZzUFia3k";
    public static final String upPath = AppConstants.botFiles.toString();

    @Autowired
    private  BotService botService;

    private Client client;
    @Override
    public String getBotUsername() {
        return "omqpBot";
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {

        Message message = update.getMessage();
        SendMessage sendMessage = new SendMessage();
        String xabar;

       sendMessage.setChatId(message.getChatId());

        if (message.getChatId() != null) {
            ResultModel result = botService.existUser(message.getChatId());
            if (result.getSuccess()){
                client= (Client) result.getObject();
                System.out.println(client.toString() ) ;
                if (message.hasText()) {

                    String text = message.getText();
                    System.out.println("1---"+text);
                    String text1 = message.getText();
                    System.out.println("2---"+text1);
                    botService.saveMessage(client,text);
                    switch (text) {
                        case "start":
                            xabar="Assalomu aleykum \n" +client.getUser().getFullName()+"\n"+
                                    "Siz  hujjat va rasmlaringizni yuborishingiz mumkin"+"\n";
                            sendMessage.setText(xabar+"\n Tizimdagi kalitingiz:"+message.getChatId());
                            break;
                        default:
                            xabar="Assalomu aleykum \n" +client.getUser().getFullName()+"\n"+
                            "Siz  hujjat va rasmlaringizni yuborishingiz mumkin"+"\n";
                            sendMessage.setText(xabar+"Tizimdagi kalitingiz:"+message.getChatId());
                    }

                }


                if (message.hasPhoto()) {
                    List<PhotoSize> photoSizeList = message.getPhoto();
                   Result photoResult= botService.pictureUpload(photoSizeList);

                   xabar="" +client.getUser().getFullName()+"\n"+
                            "Sizning  rasmlaringiz yuklandi.\n Ularni tizimdan yuklab olishlaringiz mumkin"+"\n";
                    sendMessage.setText(photoResult.getSuccess()?xabar:AppConstants.error_picture);
                }

                /**/
                if (message.hasDocument()){
                   Result docDocresult= botService.documentUpload(message.getDocument());
                    xabar="" +client.getUser().getFullName()+"\n"+
                            "Sizning  hujjatlaringiz yuklandi.\n Ularni tizimdan yuklab olishlaringiz mumkin"+"\n";
                    sendMessage.setText(docDocresult.getSuccess()?xabar:AppConstants.error_file);
                }


            }
            else {
                xabar ="Siz ro'yxatdan o'tmagansiz!!! \nIltimos kalitni adminga yuboring \n admin:  @X_Shamsiddin";
                sendMessage.setText("Assalomu aleykum\n"+xabar+"\n Kalit:  "+message.getChatId()+"\n");
            }
        }


        else {
         xabar ="Iltimos kalit adminga yuboring \n admin:  @X_Shamsiddin";
            sendMessage.setText("Assalomu aleykum\n"+"\n"+xabar+"Kalit:  "+message.getChatId());
        }




        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }


    }

/*

    java.io.File downloadPhoto(String fileUrl) {
        java.io.File file = null;
        try {
            Properties sysProps = System.getProperties();
            URL url = new URL(fileUrl);
            InputStream in = url.openStream();
            String directoryPath = sysProps.getProperty("file.separator") + sysProps.getProperty("user.home") + sysProps.getProperty("file.separator") + "Documents" + sysProps.getProperty("file.separator") + "dev";
            java.io.File directory = new java.io.File(directoryPath);

            String pathToFile = directoryPath + sysProps.getProperty("file.separator") + new Random().nextInt(100) + fileUrl.substring(fileUrl.lastIndexOf("/") + 1);

            if (!directory.exists()) {
                directory.mkdirs();
            }
            file = new java.io.File(pathToFile);
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);
            int read = 0;
            byte[] bytes = new byte[10000];
            while ((read = in.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            outputStream.flush();
            outputStream.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return file;
    }

*/

    void setButton(SendMessage sendMessage) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

        keyboardMarkup.setSelective(true);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboardRows = new ArrayList<KeyboardRow>();

        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("uz-ru"));
        row1.add(new KeyboardButton("ru-uz"));
        keyboardRows.add(row1);

        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton("ru-en"));
        row2.add(new KeyboardButton("en-ru"));
        keyboardRows.add(row2);

        KeyboardRow row3 = new KeyboardRow();
        row3.add(new KeyboardButton("ru-tr"));
        row3.add(new KeyboardButton("tr-ru"));
        keyboardRows.add(row3);
        keyboardMarkup.setKeyboard(keyboardRows);
        sendMessage.setReplyMarkup(keyboardMarkup);

    }




}
