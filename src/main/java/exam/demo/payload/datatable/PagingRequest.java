package exam.demo.payload.datatable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class PagingRequest {
    private int start;
    private int length;
    private int draw;
    private List<Order> order;
    private List<Column> columns;
    private Search search;
}
