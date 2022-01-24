package exam.demo.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SessionDestroyListener implements ApplicationListener<HttpSessionDestroyedEvent> {

    @Override
    public void onApplicationEvent(HttpSessionDestroyedEvent httpEvent) {
        httpEvent.getSecurityContexts().stream().forEach(securityContext -> {
           log.error("Session destroyed"+securityContext.getAuthentication().getPrincipal().toString());
        });
    }
}
