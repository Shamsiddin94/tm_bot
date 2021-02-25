package exam.demo.repository.bot;


import exam.demo.entity.bot.Attachment;
import exam.demo.entity.bot.Client;
import exam.demo.entity.enums.FileType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

    List<Attachment> findByClientAndTypeAndDelete(Client client, FileType picture, Boolean delete);
    Optional <Attachment> findById(Long id);
 }
