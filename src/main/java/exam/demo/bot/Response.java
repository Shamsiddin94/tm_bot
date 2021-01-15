package exam.demo.bot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    private String file_path;
    private String file_unique_id;
    private String  file_id;
    private String  file_size;

}
