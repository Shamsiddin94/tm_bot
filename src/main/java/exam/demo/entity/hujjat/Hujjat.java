package exam.demo.entity.hujjat;

import exam.demo.entity.User;
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
public class Hujjat extends AbsEntity {

    private String  docNumber;
    private String  regNumber;
    @Column(columnDefinition = "TEXT")
    private String  mazmuni;
    private Date outDate;

    @ManyToOne
    private Tashkilot tashkilot;

    @ManyToOne
    private Tasnif tasnif;

    @ManyToOne
    private User bajaruvchi;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "hujjat",cascade = CascadeType.ALL)
    List<HujjatFile>  hujjatFiles;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "hujjat",cascade = CascadeType.ALL)
    List<JavobFile>  javobFiles;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "hujjat",cascade = CascadeType.ALL)
    List<Bajarish>  bajarishList;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "hujjat",cascade = CascadeType.ALL)
    List<Tanishish>  tanishishList;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "hujjat",cascade = CascadeType.ALL)
    List<Nazorat>  nazoratList;

    public Hujjat(String docNumber, String regNumber, String mazmuni, Date outDate, Tashkilot tashkilot, Tasnif tasnif) {
        this.docNumber = docNumber;
        this.regNumber = regNumber;
        this.mazmuni = mazmuni;
        this.outDate = outDate;
        this.tashkilot = tashkilot;
        this.tasnif = tasnif;
    }
}
