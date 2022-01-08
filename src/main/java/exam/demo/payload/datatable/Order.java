package exam.demo.payload.datatable;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Order {
    private Integer column;
    private Direction direction;
}
