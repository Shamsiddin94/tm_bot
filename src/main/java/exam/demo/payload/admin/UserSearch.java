package exam.demo.payload.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSearch {
    private  boolean isSearch;
    private String fullName;
    private  String userName;
    private String  date;

    public UserSearch(boolean isSearch) {
    }
}

