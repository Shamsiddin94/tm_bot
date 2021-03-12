package exam.demo.service;

import exam.demo.entity.User;
import exam.demo.entity.enums.EntityStatus;
import exam.demo.payload.Result;
import exam.demo.payload.ResultModel;
import exam.demo.payload.admin.UserEdit;
import exam.demo.payload.admin.UserRequest;
import exam.demo.repository.RoleRepository;
import exam.demo.repository.UserRepository;
import exam.demo.service.admin.AdminSercive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AdminSercive adminSercive;

    public List<User> getAll() {
        return userRepository.findAllByState(EntityStatus.ACTIVE);
    }


    public Result Edit(UserRequest userRequest,Long uuid) {
        Result result = new Result();
       Optional<User> userOptional=userRepository.findById(uuid);
       if (userOptional.isPresent()){
          User  user=userOptional.get();
          user.setUserName(userRequest.getUserName());
          user.setFullName(userRequest.getFullName());
          user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
           user.setRoles(adminSercive.getRoleListFromId(userRequest.getList()));
           userRepository.save(user);

       }

        result.setMessage("User saqlandi");
        result.setSuccess(true);
        return result;
    }

    public Result EditSelf(UserEdit edit, User userSelf) {
        Result result = new Result();
        Optional<User> userOptional=userRepository.findById(userSelf.getId());
        if (userOptional.isPresent()){
            User  user=userOptional.get();
            user.setUserName(edit.getUserName());
            user.setFullName(edit.getFullName());
            user.setPassword(passwordEncoder.encode(edit.getPassword()));
            user.setRoles(userSelf.getRoles());
            userRepository.save(user);
            result.setMessage("Sizning o'zagrtirishingiz saqlandi");
            result.setSuccess(true);
            return result;
        }

        result.setMessage("Kechirasiz xatolik sodir bo'ldi");
        result.setSuccess(false);
        return result;
    }

    public Result delete(Long id) {
        userRepository.deleteById(id);
        return new Result(true, "User deleted");
    }


    public Result create(UserRequest userRequest) {
        Result result = new Result();
        User user=new User();
        user.setFullName(userRequest.getFullName());
        user.setUserName(userRequest.getUserName());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRoles(adminSercive.getRoleListFromId(userRequest.getList()));
       try {
           userRepository.save(user);
           result.setMessage("User saqlandi");
           result.setSuccess(true);
       }catch (Exception e)
       {
           result.setSuccess(false);
           result.setMessage(e.getMessage());
       }

        return result;

    }


    public User getUser(Long uuid) {
        Result result = new Result();
        Optional<User> user = userRepository.findById(uuid);
        if (user.isPresent()) {

            return user.get();
        }
        return user.get();
    }

    public ResultModel checkUser(String userName) {
        ResultModel result = new ResultModel();
        Optional<User> user = userRepository.findByUserName(userName);
        if (user.isPresent()) {
            result.setSuccess(false);
            result.setObject(user.get());
            result.setMessage("Ushbu user mavjud");
            return result;
        }
        result.setSuccess(true);
        result.setMessage("ushbu user mavjud");
        return result;
    }

public Page<User> getUserPage( int page,int size){

    Pageable pageable= PageRequest.of(page,size, Sort.Direction.ASC ,"createdAt");
    return  userRepository.findAll(pageable);
}

 public  Optional<User> userOpt(Long id){
    return  userRepository.findById(id);
}

}
