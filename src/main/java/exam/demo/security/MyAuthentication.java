package exam.demo.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class MyAuthentication extends AbstractAuthenticationToken {
    private Object credential;
    private Object principal;

    public MyAuthentication(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
    }

    @Override
    public Object getCredentials() {
        return credential;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    public void setCredential(Object credential) {
        this.credential = credential;
    }

    public void setPrincipal(Object principal) {
        this.principal = principal;
    }
}
