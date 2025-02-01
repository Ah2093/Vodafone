package eg.com.vodafone.security.userDetailsService;


import eg.com.vodafone.model.Customer;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class SecurityUserAdapter implements UserDetails {


    private final Customer delegate;

    @Override
    public String getUsername() {
        return this.delegate.getUserName();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("read"));
    }

    @Override
    public String getPassword() {
        return this.delegate.getPassword();
    }

    // todo
    //how to get authorities form db
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return delegate.getRoles()
//                .stream()
//                .map(SecurityAuthority::new)
//                .toList();
//    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
