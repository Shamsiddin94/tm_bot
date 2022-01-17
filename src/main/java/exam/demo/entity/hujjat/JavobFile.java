package exam.demo.entity.hujjat;

import exam.demo.entity.User;
import exam.demo.entity.identity.AbsEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class JavobFile extends AbsEntity {

    @ManyToOne
    private Tashkilot tashkilot;

    @Column(columnDefinition = "TEXT")
    private String  mazmuni;


    private String fileName;
    private String fileType;
    private String fileUrl;
    private String fileSize;

    @ManyToOne(fetch = FetchType.LAZY ,optional = false)
    private  Hujjat hujjat;
}
