package exam.demo.entity.hujjat;

import exam.demo.entity.User;
import exam.demo.entity.identity.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Tanishish extends AbsEntity {
    private User chiqaruvchi;
    private User tanishuvchi;

    private boolean isView;
    private Timestamp viewTime;
    @Column(columnDefinition = "TEXT")
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY ,optional = false)
    private Hujjat hujjat;
}
