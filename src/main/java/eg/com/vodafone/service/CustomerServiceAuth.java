package eg.com.vodafone.service;


import eg.com.vodafone.RequestDto.LogInRequest;
import eg.com.vodafone.RequestDto.RegisterRequest;
import eg.com.vodafone.ResponseDto.LogInResponse;
import eg.com.vodafone.ResponseDto.RegisterResponse;
import eg.com.vodafone.ResponseDto.UserProfileResponse;
import eg.com.vodafone.model.Customer;
import eg.com.vodafone.repository.UserRepository;
import eg.com.vodafone.security.jwt.JwtTokenProvider;
import eg.com.vodafone.security.userDetailsService.JpaUserDetailsService;
import eg.com.vodafone.util.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceAuth {

    @Autowired
    private JpaUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MapperUtil mapperUtil;

    public RegisterResponse registerUser(RegisterRequest registerRequest) {
        if (userRepository.findByUserName(registerRequest.getUserName()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        Customer customer = new Customer();

        customer.setUserName(registerRequest.getUserName());
        customer.setEmail(registerRequest.getEmail());
        customer.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        customer.setPhonenumber(registerRequest.getPhoneNumber());
        customer.setFirstName(registerRequest.getFirstName());
        customer.setLastName(registerRequest.getLastName());

        Customer saved = userRepository.save(customer);
        RegisterResponse response = new RegisterResponse();
        response.setUserId(saved.getId());
        response.setMessage("User registered successfully");
        return response;
    }

    public LogInResponse authenticateUser(LogInRequest logInRequest) {
        Customer customer = getUserByUserNameOrThrow(logInRequest.getUserName());

        if (passwordEncoder.matches(logInRequest.getPassword(), customer.getPassword())) {
            String token = jwtTokenProvider.generateToken(customer.getUserName());
            LogInResponse response = new LogInResponse();
            response.setToken(token);
            return response;
        } else {
            throw new BadCredentialsException("Invalid email or password");
        }
    }


    public UserProfileResponse getUserProfile(String username) {
        Customer customer = getUserByUserNameOrThrow(username);
        return mapperUtil.mapEntity(customer, UserProfileResponse.class);
    }

    private Customer getUserByUserNameOrThrow(String username) {
        return userRepository.findByUserName(username)
                .orElseThrow(() -> new BadCredentialsException("Invalid userName or password"));
    }
}
