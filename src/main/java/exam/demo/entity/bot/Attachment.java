package exam.demo.entity.bot;

import exam.demo.entity.enums.FileType;
import exam.demo.entity.identity.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Attachment extends AbsEntity {
    private  String fileUrl;
    private String fileName;
    private String contentType;
    @Column(nullable = true)
    private boolean delete=false;

    @Enumerated(EnumType.STRING)
    private FileType type;
    private  String size;
    private boolean isOpen=false;
    @ManyToOne(optional = false)
    private  Client client;
}
