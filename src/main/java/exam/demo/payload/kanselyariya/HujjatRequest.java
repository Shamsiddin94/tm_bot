package exam.demo.payload.kanselyariya;

import exam.demo.anotation.ValidFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class HujjatRequest {

    @NotEmpty(message = "Malumot kiriting")
    @Size(min = 5,max = 50 ,message = "Malumot uzunligi 5 dan 50 gacha simvol")
    private String docNumber;

    @NotEmpty(message = "Malumot kiriting")
    @Size(min = 5,max = 50 ,message = "Malumot uzunligi 5 dan 50 gacha simvol")
    private String regNumber;

    @NotEmpty(message = "Malumot kiriting")
    private String mazmuni;

    @NotNull(message = "Chiqish sanasini kiriting")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate outDate;


    @ValidFile
    private MultipartFile[] files;


    @NotNull(message = "Bajaruvchini kiriting!")
    private   Long bajaruvchi;

    @NotNull(message = "Tasnifini kiriting!")
    private   Long  tasnif;

    @NotNull(message = "Tashkilotini kiriting!")
    private   Long  tashkilot;



}
