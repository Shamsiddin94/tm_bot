package exam.demo;

import exam.demo.bot.TelegramBot;
import exam.demo.exception.StorageException;
import exam.demo.utils.AppConstants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.IOException;
import java.nio.file.Files;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {

        /*Telegram bot*/




        if (!Files.exists(AppConstants.botFiles)) {
            try {

               Files.createDirectories(AppConstants.botFiles);
                System.out.println(AppConstants.botFiles+"--papka yaratildi");

            } catch (IOException e) {
                throw new StorageException("Papka yaritshda xatolik", e);
            }

        }
        if (!Files.exists(AppConstants.botFileSend)) {
            try {

                Files.createDirectories(AppConstants.botFileSend);
                System.out.println(AppConstants.botFileSend+"--papka yaratildi");

            } catch (IOException e) {
                throw new StorageException("Papka yaritshda xatolik", e);
            }

        }


        if (!Files.exists(AppConstants.hujjat)) {
          try {
        Files.createDirectories(AppConstants.hujjat);

              System.out.println(AppConstants.hujjat+"--papka yaratildi");

    } catch (IOException e) {
        throw new StorageException("Papka yaritshda xatolik", e);
    }

            }


        SpringApplication.run(DemoApplication.class, args);
    }

}
