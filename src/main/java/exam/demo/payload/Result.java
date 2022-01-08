package exam.demo.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private Boolean success;
    private Boolean type;
    private ResponseType rs;
    private String message;

    public Result(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Result(boolean b, boolean b1, String message) {
        this.success=b;
        this.type=b1;
        this.message=message;
    }
}
