package exam.demo.payload.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEdit {

    @Size(min=5, max=50, message = "Malumot 5 ta harfdan 50 tagacha bo'lishi kerak")
    private String fullName;

    @Size(min=4, max=50, message = "Username  5 ta harfdan 50 tagacha bo'lishi kerak")
    private String userName;

    @Size(min=5, max=30, message = "Parol 5 ta harfdan 50 tagacha bo'lishi kerak")
    private String password;


    @Size(min=5, max=50, message = "Takroriy parol 5 ta harfdan 50 tagacha bo'lishi kerak")
    private String prePassword;






    public  boolean isValid(){
        return this.password.equals(prePassword);
    }
}
