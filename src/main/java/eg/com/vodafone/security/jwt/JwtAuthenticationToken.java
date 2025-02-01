package eg.com.vodafone.security.jwt;

import eg.com.vodafone.security.userDetailsService.SecurityUserAdapter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@AllArgsConstructor

public class JwtAuthenticationToken implements CredentialsContainer, Authentication {

    private final SecurityUserAdapter principal;
    private boolean authenticated;
    private String token;

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        throw new UnsupportedOperationException("JwtAuthenticationToken is immutable");
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.principal.getAuthorities();
    }

    @Override
    public String getCredentials() {
        return token;
    }

    @Override
    public Object getDetails() {
        return null; // You can implement this if you need to provide additional details
    }

    @Override
    public SecurityUserAdapter getPrincipal() {
        return this.principal;
    }

    @Override
    public String getName() {
        return this.principal == null ? null : this.principal.getUsername();
    }

    // Allows the ProviderManager to erase the credentials so they don't remain in-memory
    @Override
    public void eraseCredentials() {
        this.token = null;
    }
}
