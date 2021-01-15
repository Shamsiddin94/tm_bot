package exam.demo.entity;

import exam.demo.entity.enums.EntityStatus;
import exam.demo.entity.identity.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
public class Region extends AbsEntity {
    private String name;

    @Column(nullable = true)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private Country country;

    public Region(String name, String description, Country country) {
        this.name = name;
        this.description = description;
        this.country = country;
    }
}
