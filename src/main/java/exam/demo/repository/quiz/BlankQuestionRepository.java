package exam.demo.repository.quiz;

import exam.demo.entity.enums.EntityStatus;
import exam.demo.entity.quiz.BlankQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BlankQuestionRepository extends JpaRepository<BlankQuestion,Long> {


  List<BlankQuestion> getAllByState(EntityStatus entityStatus);

    @Query("SELECT p FROM BlankQuestion p WHERE concat(p.id,'') LIKE %?1%"
            + " and concat(p.num,'') LIKE %?2%"
            + " and p.textNumber LIKE %?3%"
            + " and p.text LIKE %?4%"
            + " and CONCAT(p.type, '') LIKE %?5%")
    List<BlankQuestion> searchAllFields(@Param("id") String id,
                                          @Param("num") String  num,
                                          @Param("textNumber") String  textNumber,
                                          @Param("text") String text,
                                          @Param("type") String type);

}
