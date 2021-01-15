package exam.demo.payload.admin;

import exam.demo.entity.Role;
import exam.demo.entity.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @Size(min=5, max=50, message = "Full Name must be  5 and 50")
    private String fullName;
    @Size(min=5, max=50, message = "Username must be  6 and 50")
    @NotNull(message = "your role can not be empty !")
    private String userName;
    @Size(min=5, max=30, message = "Password must be  5 and 30")
    private String password;
    @NotNull(message = "your role can not be empty !")
    private Integer role;

    @Size(min=5, max=50, message = "prePassword must be  5 and 50")
    private String prePassword;

    public UserRequest(@Size(min = 6, max = 50,
                        message = "Full Name must be  5 and 50") String fullName,
                       @Size(min = 5, max = 50, message = "Username must be  6 and 50") String userName, @Size(min = 5,
            max = 30, message = "Password must be  5 and 30") String password, String prePassword) {
        this.fullName = fullName;
        this.userName = userName;
        this.password = password;
        this.prePassword = prePassword;
    }
    public UserRequest(String fullName,String userName,Integer role) {
        this.fullName = fullName;
        this.userName = userName;
        this.role = role;

    }

    public UserRequest(@Size(min = 5, max = 50, message = "Full Name must be  5 and 50") String fullName, @Size(min = 5, max = 50, message = "Username must be  6 and 50") @NotNull(message = "your role can not be empty !") String userName) {
        this.fullName = fullName;
        this.userName = userName;
    }

    @AssertTrue(message = "teng bulish kerak")
    public  boolean isValid(){
        return this.password.equals(prePassword);
    }
}
