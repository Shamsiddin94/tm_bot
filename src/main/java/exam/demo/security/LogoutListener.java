package exam.demo.security;

import exam.demo.entity.User;
import exam.demo.entity.UserLog;
import exam.demo.entity.enums.AuditType;
import exam.demo.repository.UserLogRepository;
import exam.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class LogoutListener  implements ApplicationListener<LogoutSuccessEvent> {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private UserService userService;
    @Autowired
    private UserLogRepository userLogRepository;
    private static final Logger LOG = LoggerFactory.getLogger(LoginListener.class);
    @Override
    public void onApplicationEvent(LogoutSuccessEvent logoutSuccessEvent) {
        UserDetails ud = (UserDetails) logoutSuccessEvent.getAuthentication().getPrincipal();
        UserLog userLog=new UserLog();
        userLog.setUsername(ud.getUsername());
        userLog.setIpAdrees(request.getRemoteAddr());
        userLog.setAuditType(AuditType.CHIQISH);
        userLog.setUser((User) userService.checkUser(ud.getUsername()).getObject());
        userLogRepository.save(userLog);

        LOG.info(" chiqish  User " + ud.getUsername() + " logged in successfully");
    }
}
