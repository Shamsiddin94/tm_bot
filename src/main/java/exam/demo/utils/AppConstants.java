package exam.demo.utils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;

public interface AppConstants {
    String DEFAULT_PAGE_NUMBER = "0";
    String DEFAULT_PAGE_SIZE = "10";
    int MAX_PAGE_SIZE = 20;
    Path hujjat = Paths.get("storage/hujjat");

    Path botFiles = Paths.get("botFiles/received");
    Path botFileSend = Paths.get("botFiles/send");


    //String botToken="1717566005:AAFllcz2SrdGX-6U9LrI_c5AHyQHPEVzW3U";
    String botToken="1603310063:AAFK4wl7Dn-aHLX4EnqsDBTB4Gfm0sBTMQQ";
   // String botName="omqpBot";
    String botName="OmqpFileBot";

    String error_file="Ushbu fayl qabul qilinmadi. Fayl xajmi 20 mb dan oshmasligi zarur !!!";
    String error_picture="Ushbu rasm qabul qilinmadi. Rasm xajmi 5 mb dan oshmasligi zarur !!!";

}
