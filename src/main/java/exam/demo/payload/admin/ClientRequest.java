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
public class ClientRequest {

    @NotNull(message = "Chat idini kiriting!")

    private Long chatId;

    @NotEmpty(message = "Malumot kiriting")
    @Size(min = 3,max = 50 ,message = "Malumot uzunligi 3 dan 50 gacha simvol")
    private String userName;

    @NotEmpty(message = "Malumot kiriting")
    @Size(min = 3,max = 50 ,message = "Malumot uzunligi 3 dan 50 gacha simvol")
    private String  firstName;

    @NotEmpty(message = "Malumot kiriting")
    @Size(min = 3,max = 50 ,message = "Malumot uzunligi 3 dan 50 gacha simvol")
    private String  lastName;

    @NotNull(message = "Userni  tanlang!")
    private  Long userId;

}
