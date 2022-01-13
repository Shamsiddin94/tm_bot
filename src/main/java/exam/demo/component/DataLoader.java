package exam.demo.component;

import exam.demo.entity.Country;
import exam.demo.entity.User;
import exam.demo.entity.bot.Client;
import exam.demo.entity.enums.RoleName;
import exam.demo.entity.quiz.BlankForm;
import exam.demo.entity.quiz.BlankQuestion;
import exam.demo.entity.quiz.QuizType;
import exam.demo.repository.RoleRepository;
import exam.demo.repository.UserRepository;
import exam.demo.repository.bot.AttachmentRepository;
import exam.demo.repository.bot.ClientRepository;
import exam.demo.repository.quiz.BlankFormRepository;
import exam.demo.repository.quiz.BlankQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class DataLoader implements CommandLineRunner {
    @Value("${spring.datasource.initialization-mode}")
    private String initialMode;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private BlankFormRepository blankFormRepository;

    @Autowired
    private BlankQuestionRepository questionRepository;



    @Override
    public void run(String... args) throws Exception {
       /* for (int i=0;i<=10;i++){
            BlankForm blankForm=new  BlankForm();
            blankForm.setDescription("description"+i);
            blankForm.setName("name"+i);
          
            blankFormRepository.save(blankForm);

        }*/
        for (int i=0;i<=10;i++){
            BlankQuestion blankQuestion=new BlankQuestion();
            blankQuestion.setNum((long) (10+i));
            blankQuestion.setTextNumber("number"+i);
            blankQuestion.setText("Textsdrfsf_"+i);
            blankQuestion.setType(QuizType.SHORT);
            questionRepository.save(blankQuestion);

        }


      if (initialMode.equals("always")) {
          PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = new User("Shamsiddin xushbaqtov", "shamsiddin",passwordEncoder.encode("12345"), roleRepository.findByName(RoleName.ROLE_ADMIN));
        User user1 = new User("Shamsiddin xushbaqtov", "user",passwordEncoder.encode("12345"), roleRepository.findByName(RoleName.ROLE_USER));
        User user2 = new User("murojaat", "murojaat",passwordEncoder.encode("12345"), roleRepository.findByName(RoleName.ROLE_MUROJAAT));
        User user3 = new User("rahbariyat", "rahbariyat",passwordEncoder.encode("12345"), roleRepository.findByName(RoleName.ROLE_RAHBARIYAT));
        User user4 = new User("kanselyariya", "kanselyariya",passwordEncoder.encode("12345"), roleRepository.findByName(RoleName.ROLE_KANSELYARIYA));
        User user5 = new User("spiker", "spiker",passwordEncoder.encode("12345"), roleRepository.findByName(RoleName.ROLE_SPIKER));
        User user6 = new User("kadr", "kadr",passwordEncoder.encode("12345"), roleRepository.findByName(RoleName.ROLE_KADR));

          userRepository.save(user);
          userRepository.save(user1);
          userRepository.save(user2);
          userRepository.save(user3);
          userRepository.save(user4);
          userRepository.save(user5);
          userRepository.save(user6);
          Client client=new Client();
          List<Client> clients=new ArrayList<>();
          client.setChatId((long) 551695315);
          client.setFirstName("Shamsiddin");
          client.setLastName("dddd");
          client.setUserName("");
          client.setUser(user);
          clients.add(client);
          clientRepository.save(client);
        /* Optional<User>  userOptional=userRepository.findByUserName(user.getUsername());
          if (userOptional.isPresent()){
              User userTem=userOptional.get();
              userTem.setClients(clients);
              userRepository.save(userTem);
          }*/


      }
    }
}
