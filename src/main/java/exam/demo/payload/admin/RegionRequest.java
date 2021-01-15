package exam.demo.payload.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegionRequest {
    @Size(min = 5,max = 50 ,message = "Malumot uzunligi 5 dan 50 gacha simvol")
    @NotEmpty(message = "Malumot kiriting")
    private String name;
    @Size(min = 5,max = 50 ,message = "Malumot uzunligi 5 dan 50 gacha simvol")
    @NotEmpty(message = "Description kiriting")
    private String description;
    @NotNull(message = "your role can not be empty !")
    private   Long countryId;

}
