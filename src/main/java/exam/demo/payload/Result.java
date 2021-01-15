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
    private Boolean type=false;
    private String message;

    public Result(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
