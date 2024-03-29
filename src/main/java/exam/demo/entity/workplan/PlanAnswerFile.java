package exam.demo.entity.workplan;

import exam.demo.entity.identity.AbsEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class  PlanAnswerFile extends AbsEntity {

    private String fileName;
    private String fileType;
    private String fileUrl;
    private String fileSize;

    @ManyToOne(fetch = FetchType.LAZY ,optional = false)
    private Plan plan;
}
