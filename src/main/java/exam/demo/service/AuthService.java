package exam.demo.service;

import exam.demo.repository.RoleRepository;
import exam.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService  implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
   RoleRepository roleRepository;


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
       /* System.out.println(userRepository
                .findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException(userName)));*/
        return userRepository.findByUserName(userName).orElseThrow(()->
                new UsernameNotFoundException("ushbu"+userName+" login topilmadi"));
    }

    public boolean isAuthenticate(){
        return (SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
    }
}
