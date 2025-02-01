package eg.com.vodafone.security.jwt;

import eg.com.vodafone.security.userDetailsService.JpaUserDetailsService;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JwtAuthenticationConfigurer extends AbstractHttpConfigurer<JwtAuthenticationConfigurer, HttpSecurity> {
    @Override
    public void configure(HttpSecurity http) {
        JpaUserDetailsService userDetailsService = http
                .getSharedObject(ApplicationContext.class)
                .getBean(JpaUserDetailsService.class);
        JwtTokenProvider jwtTokenProvider = http
                .getSharedObject(ApplicationContext.class)
                .getBean(JwtTokenProvider.class);

        JwtAuthenticationProvider apiKeyAuthenticationProvider = new JwtAuthenticationProvider(userDetailsService, jwtTokenProvider);

        http.authenticationProvider(apiKeyAuthenticationProvider);
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);

        JwtAuthenticationFilter apiKeyAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager);
        http.addFilterBefore(apiKeyAuthenticationFilter, BasicAuthenticationFilter.class);
    }
}
