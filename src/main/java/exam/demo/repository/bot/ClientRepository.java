package exam.demo.repository.bot;

import exam.demo.entity.User;
import exam.demo.entity.bot.Client;
import exam.demo.entity.enums.EntityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {
  Optional<Client>  findByChatId(Long chatId);
  List<Client> findByUserAndState(User user, EntityStatus entityStatus);
}
