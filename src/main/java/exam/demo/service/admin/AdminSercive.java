package exam.demo.service.admin;

import exam.demo.entity.Role;
import exam.demo.entity.User;
import exam.demo.entity.enums.EntityStatus;
import exam.demo.payload.Result;
import exam.demo.payload.admin.UserRequest;
import exam.demo.repository.RoleRepository;
import exam.demo.repository.UserRepository;
import exam.demo.repository.specs.SearchCriteria;
import exam.demo.repository.specs.SearchOperation;
import exam.demo.repository.specs.UsersSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminSercive {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public  List<Role> getRoleListFromId(List<Integer> ids){

        return  roleRepository.findAllById(ids);
    }
    public List<Integer> getIdsFromRole(List<Role> roles){
        List<Integer> tempId=new ArrayList<>();
        roles.forEach(role -> {
            tempId.add(role.getId());
        });
        return  tempId;
    }
    public List<Role> getRoles(){
        return roleRepository.findAll();
    }
    public  Optional <Role> getRole(Integer role){
        return roleRepository.findById(role);
    }
    public Page<User> getUsers(int page, int size){
        Pageable pageable= PageRequest.of(page,size);

        return  userRepository.findAllByState(EntityStatus.ACTIVE,pageable);
    }

    public Page<User> getUsersSearch(String s1){
        Pageable pageable= PageRequest.of(0,100);
        UsersSpecification usersSpecification=new UsersSpecification();
        usersSpecification.add(new SearchCriteria("fullName",s1, SearchOperation.MATCH));
        UsersSpecification usersSpecification1=new UsersSpecification();

        usersSpecification1.add(new SearchCriteria("userName",s1, SearchOperation.MATCH));

        Page<User> userPage=userRepository.search(s1,EntityStatus.ACTIVE,pageable);
        /*findAll(Specification.where(usersSpecification)
                .or(usersSpecification1),pageable);*/


        return  userPage;
    }

    public Optional<User> getUser(Long uuid){
        return userRepository.findById(uuid);
    }

    public Result addUser(UserRequest userRequest){
        Optional<User> userOptional=userRepository.findByUserName(userRequest.getUserName());
        Result result=new Result();
        if (userOptional.isPresent()){
            result.setSuccess(false);
            result.setMessage("Ushbu nomli user mavjud");
            return result;
        }

        //System.out.println(userRequest.toString());
              User user=new User(userRequest.getFullName(),
                userRequest.getUserName(),
                passwordEncoder.encode(userRequest.getPassword()),
                getRoleListFromId(userRequest.getList()));
      //  System.out.println(user.toString());
        userRepository.save(user );
                result.setSuccess(true);
                result.setMessage("User saqlandi");
                return  result;
    }

    public Result editUser(Long uuid,UserRequest userRequest){

        Optional<User> userOptional=userRepository.findByUserName(userRequest.getUserName());
        Result result=new Result();
        if (userOptional.isPresent() && !userOptional.get().getId().equals(uuid)){
            result.setSuccess(false);
            result.setMessage("Ushbu nomli boshqa user mavjud boshqa  login kiriting");
            return result;
        }
       User user=userRepository.getOne(uuid);

        user.setFullName(userRequest.getFullName());
        user.setUserName( userRequest.getUserName());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRoles( getRoleListFromId(userRequest.getList()));
        userRepository.save(user );

        System.out.println(userRepository.findById(uuid).get().toString());

        result.setSuccess(true);
        result.setMessage("User  o'zgartirildi");
        return  result;
    }

    public  Result delete(Long id){
      userRepository.deleteById(id);
      return new Result(true,"User deleted");
}

   public List<User> findAll(){
        return  userRepository.findAllByState(EntityStatus.ACTIVE);
   }
}
