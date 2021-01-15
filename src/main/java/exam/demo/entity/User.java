package exam.demo.entity;

import exam.demo.entity.bot.Client;
import exam.demo.entity.enums.EntityStatus;
import exam.demo.entity.identity.AbsEntity;
import exam.demo.entity.tizim.Bulim;
import exam.demo.entity.tizim.Fraksiya;
import exam.demo.entity.tizim.Lavozim;
import exam.demo.entity.tizim.Qumita;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User extends AbsEntity implements UserDetails {

    @Column(nullable = false)
    private String fullName;
    @Column(nullable = false)
    private String userName;
    @Column(nullable = false)
    private String password;



    /*tizim*/
    @ManyToOne
    private Bulim bulim;

    @ManyToOne
    private Fraksiya fraksiya;

    @ManyToOne
    private Qumita qumita;

    @ManyToOne
    private Lavozim lavozim;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",joinColumns = {@JoinColumn(name = "user_id")},
        inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private List<Role> roles;


    public User(String fullName, String userName, String password, List<Role> roles) {
        this.fullName = fullName;
        this.userName = userName;
        this.password = password;
        this.roles = roles;
    }


    /*Security*/
    private Boolean accountNonExpired=true;
    private Boolean accountNonLocked =true;
    private Boolean credentialNonExpired =true;
    private Boolean enabled=true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
