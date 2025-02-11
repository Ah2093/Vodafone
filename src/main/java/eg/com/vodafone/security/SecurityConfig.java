package eg.com.vodafone.security;

import eg.com.vodafone.security.jwt.JwtAuthenticationConfigurer;
import eg.com.vodafone.security.passwordEncoder.PasswordEncoderConfig;
import eg.com.vodafone.security.userDetailsService.JpaUserDetailsServiceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Import({
        PasswordEncoderConfig.class,
        JpaUserDetailsServiceConfig.class,
})
@EnableMethodSecurity
public class SecurityConfig {

    private static void configureEndpointSecurity(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(r -> r
                .requestMatchers("/api/auth/login", "/api/auth/register").permitAll()
                .requestMatchers("/view/login", "/view/register").permitAll()
                .requestMatchers("/view/welcome").hasAuthority("read")
                .requestMatchers("/css/**", "/js/**").permitAll()
                .anyRequest().authenticated()
        );

    }

    private static void configureCsrf(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
    }

    private static void configureSessionManagement(HttpSecurity http) throws Exception {
        http.sessionManagement(s -> s
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );
    }

    private static void configureJwtAuthentication(HttpSecurity http) throws Exception {
        http.with(new JwtAuthenticationConfigurer(), Customizer.withDefaults());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        configureEndpointSecurity(http);
        configureCsrf(http);
        configureSessionManagement(http);
        configureJwtAuthentication(http);
        return http.build();
    }
}
