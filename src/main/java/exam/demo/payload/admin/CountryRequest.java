package exam.demo.payload.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryRequest {

    @NotEmpty(message = "Malumot kiriting")
    @Size(min = 5,max = 50 ,message = "Malumot uzunligi 5 dan 50 gacha simvol")
    private String name;

    @NotEmpty(message = "Description kiriting")
    private String description;


}
