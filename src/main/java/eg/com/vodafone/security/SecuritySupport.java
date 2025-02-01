package eg.com.vodafone.security;

import eg.com.vodafone.security.userDetailsService.SecurityUserAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Set;
import java.util.stream.Collectors;

public class SecuritySupport {

    private static SecurityUserAdapter getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof SecurityUserAdapter securityUserAdapter)) {
            throw new IllegalStateException("Principal must be instanceof SecurityUser");
        }
        return securityUserAdapter;
    }

    public static Set<String> getCurrentUserAuthorities() {
        return getCurrentUser().getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
    }
}
