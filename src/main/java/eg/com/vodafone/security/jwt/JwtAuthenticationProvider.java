package eg.com.vodafone.security.jwt;


import eg.com.vodafone.security.userDetailsService.JpaUserDetailsService;
import eg.com.vodafone.security.userDetailsService.SecurityUserAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JpaUserDetailsService userDetailsService;

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public JwtAuthenticationProvider(JpaUserDetailsService userDetailsService, JwtTokenProvider jwtTokenProvider) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = authentication.getCredentials().toString();
        String email = jwtTokenProvider.getUserUserNameFromToken(token);
        SecurityUserAdapter userDetails = userDetailsService.loadUserByUsername(email);
        if (userDetails != null && jwtTokenProvider.validateToken(token)) {
            return new JwtAuthenticationToken(userDetails, true, token);
        }
        throw new AuthenticationException("Invalid JWT Token or user not found") {
        };
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
