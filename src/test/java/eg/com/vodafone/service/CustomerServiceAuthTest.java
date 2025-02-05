package eg.com.vodafone.service;

import eg.com.vodafone.dto.LogInRequest;
import eg.com.vodafone.dto.RegisterRequest;
import eg.com.vodafone.dto.LogInResponse;
import eg.com.vodafone.dto.RegisterResponse;
import eg.com.vodafone.dto.UserProfileResponse;
import eg.com.vodafone.model.Customer;
import eg.com.vodafone.repository.UserRepository;
import eg.com.vodafone.security.jwt.JwtTokenProvider;
import eg.com.vodafone.security.userDetailsService.JpaUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceAuthTest {

    @InjectMocks
    private CustomerServiceAuth customerServiceAuth;

    @Mock
    private JpaUserDetailsService userDetailsService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper mapper;

    private Customer customer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customer = Customer.builder()
                .id(1L)
                .userName("testuser")
                .email("test@example.com")
                .password("password")
                .firstName("Test")
                .lastName("User")
                .phoneNumber("01012345678")
                .build();
    }

    @Test
    void testRegisterUser_Success() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUserName("testuser");
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("password");
        registerRequest.setPhoneNumber("01012345678");
        registerRequest.setFirstName("Test");
        registerRequest.setLastName("User");

        when(userRepository.findByUserName(registerRequest.getUserName())).thenReturn(java.util.Optional.empty());
        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(Customer.class))).thenReturn(customer);

        RegisterResponse response = customerServiceAuth.registerUser(registerRequest);

        assertNotNull(response);
        assertEquals("User registered successfully", response.getMessage());
        assertEquals(customer.getId(), response.getUserId());
    }

    @Test
    void testRegisterUser_EmailAlreadyRegistered() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUserName("testuser");
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("password");
        registerRequest.setPhoneNumber("01012345678");
        registerRequest.setFirstName("Test");
        registerRequest.setLastName("User");

        when(userRepository.findByUserName(registerRequest.getUserName())).thenReturn(java.util.Optional.of(customer));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> customerServiceAuth.registerUser(registerRequest));
        assertEquals("Email already registered", exception.getMessage());
    }

    @Test
    void testAuthenticateUser_Success() {
        LogInRequest logInRequest = new LogInRequest();
        logInRequest.setUserName("testuser");
        logInRequest.setPassword("password");

        when(userRepository.findByUserName(logInRequest.getUserName())).thenReturn(java.util.Optional.of(customer));
        when(passwordEncoder.matches(logInRequest.getPassword(), customer.getPassword())).thenReturn(true);
        when(jwtTokenProvider.generateToken(customer.getUserName())).thenReturn("mockToken");

        LogInResponse response = customerServiceAuth.authenticateUser(logInRequest);

        assertNotNull(response);
        assertEquals("mockToken", response.getToken());
    }

    @Test
    void testAuthenticateUser_InvalidPassword() {
        LogInRequest logInRequest = new LogInRequest();
        logInRequest.setUserName("testuser");
        logInRequest.setPassword("wrongPassword");

        when(userRepository.findByUserName(logInRequest.getUserName())).thenReturn(java.util.Optional.of(customer));
        when(passwordEncoder.matches(logInRequest.getPassword(), customer.getPassword())).thenReturn(false);

        BadCredentialsException exception = assertThrows(BadCredentialsException.class, () -> customerServiceAuth.authenticateUser(logInRequest));
        assertEquals("Invalid email or password", exception.getMessage());
    }

    @Test
    void testAuthenticateUser_UserNotFound() {
        LogInRequest logInRequest = new LogInRequest();
        logInRequest.setUserName("nonExistentUser");
        logInRequest.setPassword("password");

        when(userRepository.findByUserName(logInRequest.getUserName())).thenReturn(java.util.Optional.empty());

        BadCredentialsException exception = assertThrows(BadCredentialsException.class, () -> customerServiceAuth.authenticateUser(logInRequest));
        assertEquals("Invalid userName or password", exception.getMessage());
    }

    @Test
    void testGetUserProfile_Success() {
        when(userRepository.findByUserName(customer.getUserName())).thenReturn(java.util.Optional.of(customer));
        when(mapper.map(customer, UserProfileResponse.class)).thenReturn(new UserProfileResponse());

        UserProfileResponse response = customerServiceAuth.getUserProfile(customer.getUserName());

        assertNotNull(response);
    }

    @Test
    void testGetUserProfile_UserNotFound() {
        when(userRepository.findByUserName("nonExistentUser")).thenReturn(java.util.Optional.empty());

        BadCredentialsException exception = assertThrows(BadCredentialsException.class, () -> customerServiceAuth.getUserProfile("nonExistentUser"));
        assertEquals("Invalid userName or password", exception.getMessage());
    }
}
