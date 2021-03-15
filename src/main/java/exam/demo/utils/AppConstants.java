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


    String botToken="1628959755:AAFm-DntbUW1QHJ0V11mCgw4ZE7QKKMEfE8";
    String botName="omqpBot";

    String error_file="Ushbu fayl qabul qilinmadi. Fayl xajmi 20 mb dan oshmasligi zarur !!!";
    String error_picture="Ushbu rasm qabul qilinmadi. Rasm xajmi 5 mb dan oshmasligi zarur !!!";

}
