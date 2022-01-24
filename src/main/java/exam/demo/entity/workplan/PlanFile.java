package exam.demo.entity.workplan;

import exam.demo.entity.identity.AbsEntity;
import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PlanFile extends AbsEntity {
    private String fileName;
    private String fileType;
    private String fileUrl;
    private String fileSize;

    @OneToMany
    private List<PlanFile> planFiles;

    @ManyToOne(fetch = FetchType.LAZY ,optional = false)
    private Plan plan;
}
