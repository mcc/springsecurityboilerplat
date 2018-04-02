package hk.mcc.sample.spring.security;

import hk.mcc.sample.spring.entity.SampleRole;
import hk.mcc.sample.spring.entity.SampleUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class SampleUserDetails implements UserDetails {


    private static final long serialVersionUID = 1L;

    private final Collection<GrantedAuthority> authorities;

    private final String password;

    private final String username;

    //private final String secret;

    private final boolean enabled;

    private final Long userDbId;

    private final boolean locked;

    private final boolean expired;

    public SampleUserDetails(SampleUser sampleUser){
        this.password = sampleUser.getEncodedPassword();
        this.username = sampleUser.getUserName();
        this.enabled = true;
        this.userDbId = sampleUser.getSampleUserId();
        this.locked = (sampleUser.getFailedLoginAttempt() >= 3);
        this.expired = false;
        this.authorities = new ArrayList<>();
        for(SampleRole sampleRole : sampleUser.getRoles()){
            this.authorities.add(new SimpleGrantedAuthority(sampleRole.getRoleName()));
        }
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !this.expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SampleUserDetails that = (SampleUserDetails) o;
        return Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {

        return Objects.hash(username);
    }
}
