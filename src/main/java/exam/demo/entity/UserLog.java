package exam.demo.entity;

import exam.demo.entity.enums.AuditType;
import exam.demo.entity.identity.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserLog extends AbsEntity {

    private String username;
    private String ipAdrees;

    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp timestamp;



    @Enumerated(EnumType.STRING)
    private AuditType auditType;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
