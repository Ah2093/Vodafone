package eg.com.vodafone.security.userDetailsService;

import eg.com.vodafone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public SecurityUserAdapter loadUserByUsername(String username) {
        return userRepository.findByUserName(username)
                .map(SecurityUserAdapter::new)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found " + username));
    }

}
