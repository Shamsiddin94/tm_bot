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

    String error_file="Ushbu fayl qabul qilinmadi. Fayl xajmi 20 mb dan oshmasligi zarur !!!";
    String error_picture="Ushbu rasm qabul qilinmadi. Rasm xajmi 5 mb dan oshmasligi zarur !!!";

}
