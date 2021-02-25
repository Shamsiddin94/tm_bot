package exam.demo.entity.bot;

import exam.demo.entity.identity.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Message  extends AbsEntity {



    @Column(nullable =false, columnDefinition = "text")
    private  String message;

    private boolean delete=false;
    private boolean open=false;
    @ManyToOne(optional = false)
    private  Client client;
}
