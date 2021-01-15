package exam.demo.entity.bot;

import exam.demo.entity.User;
import exam.demo.entity.identity.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Client  extends AbsEntity {
    @Column(nullable = false,unique = true)
    private Long chatId;
    private String userName;
    private String  firstName;
    private String  lastName;
    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    private User user;
}
