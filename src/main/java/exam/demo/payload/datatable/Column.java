package exam.demo.payload.datatable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Column {
    private String data;
    private String name;
    private Boolean searchable;
    private Boolean orderable;
    private Search search;

    public Column(String data) {
        this.data = data;
    }
}
