package exam.demo.entity.workplan;

import exam.demo.entity.User;
import exam.demo.entity.identity.AbsEntity;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Assigning extends AbsEntity {

    @ManyToOne
    private User kimdan;

    @ManyToOne
    private User kimga;

    @Column(columnDefinition = "TEXT")
    private String  comment;

    private Date muddat;

    private Boolean isView;

    @ManyToOne(fetch = FetchType.LAZY ,optional = false)
    private Plan plan;


    public Assigning(User kimdan, User kimga, String comment) {
        this.kimdan = kimdan;
        this.kimga = kimga;
        this.comment = comment;
    }
}
