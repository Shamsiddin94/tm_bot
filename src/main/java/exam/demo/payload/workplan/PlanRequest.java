package exam.demo.payload.workplan;

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


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanRequest {


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





}
