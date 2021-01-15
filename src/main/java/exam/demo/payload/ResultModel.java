package exam.demo.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ResultModel extends Result {
    private  Object object;
    private Map<String,String> data;

    public ResultModel(boolean success,String message, Object object){
        super(success,message);
        this.object=object;
    }

}
