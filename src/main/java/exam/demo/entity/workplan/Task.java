package exam.demo.entity.workplan;

import exam.demo.entity.enums.Status;
import exam.demo.entity.identity.AbsEntity;
import groovy.transform.NamedParam;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Task  extends AbsEntity {

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status=Status.Бириктирилмаган;

    @ManyToOne(fetch=FetchType.LAZY, cascade={CascadeType.PERSIST, CascadeType.MERGE})
    private Task task;


    @OneToMany(mappedBy = "task")
    private List<Task> tasks;


    @OneToMany(fetch = FetchType.LAZY)
    private List<PlanFile> planFiles;


    @ManyToOne(fetch = FetchType.LAZY ,optional = false)
    private Plan plan;

}
