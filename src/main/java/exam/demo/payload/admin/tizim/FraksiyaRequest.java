package exam.demo.payload.admin.tizim;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FraksiyaRequest {
    @Size(min = 5,max = 50 ,message = "Malumot uzunligi 5 dan 50 gacha simvol")
    @NotEmpty(message = "Malumot kiriting")
    private String name;


}
