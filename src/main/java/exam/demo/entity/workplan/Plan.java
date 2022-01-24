package exam.demo.entity.workplan;

import exam.demo.entity.enums.Status;
import exam.demo.entity.identity.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Plan extends AbsEntity {

    private String regNumber;

    @Column(columnDefinition = "TEXT")
    private String mazmuni;

    private Date outDate;

    //For saving files
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "plan", cascade = CascadeType.ALL)
    List<PlanFile> planFiles;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "plan", cascade = CascadeType.ALL)
    List<PlanAnswer> planAnswers;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "plan", cascade = CascadeType.ALL)
    List<Assigning> assigningList;


    public Plan(String docNumber, String regNumber,
                String mazmuni,
                Date outDate, Tashkilot tashkilot, Tasnif tasnif) {
        this.regNumber = regNumber;
        this.mazmuni = mazmuni;
        this.outDate = outDate;

    }
}
