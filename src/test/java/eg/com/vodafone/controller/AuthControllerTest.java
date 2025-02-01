package eg.com.vodafone.controller;

import eg.com.vodafone.dto.LogInRequest;
import eg.com.vodafone.dto.RegisterRequest;
import eg.com.vodafone.dto.LogInResponse;
import eg.com.vodafone.dto.RegisterResponse;
import eg.com.vodafone.service.CustomerServiceAuth;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class AuthControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private AuthController authController;

    @Mock
    private CustomerServiceAuth customerServiceAuth;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    void testRegister_Success() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUserName("testuser");
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("password");
        registerRequest.setPhoneNumber("01023456789");
        registerRequest.setFirstName("Test");
        registerRequest.setLastName("User");

        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setUserId(1L);
        registerResponse.setMessage("User registered successfully");

        when(customerServiceAuth.registerUser(any(RegisterRequest.class))).thenReturn(registerResponse);

        mockMvc.perform(post("/api/auth/register")
                        .contentType("application/json")
                        .content("{\"userName\":\"testuser\", \"email\":\"test@example.com\", \"password\":\"password\", \"phoneNumber\":\"01023456789\", \"firstName\":\"Test\", \"lastName\":\"User\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("User registered successfully"))
                .andExpect(jsonPath("$.userId").value(1));
    }

    @Test
    void testRegister_Failure_EmailAlreadyRegistered() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUserName("testuser");
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("password");
        registerRequest.setPhoneNumber("01023456789");
        registerRequest.setFirstName("Test");
        registerRequest.setLastName("User");

        when(customerServiceAuth.registerUser(any(RegisterRequest.class)))
                .thenThrow(new RuntimeException("Email already registered"));

        mockMvc.perform(post("/api/auth/register")
                        .contentType("application/json")
                        .content("{\"userName\":\"testuser\", \"email\":\"test@example.com\", \"password\":\"password\", \"phoneNumber\":\"01023456789\", \"firstName\":\"Test\", \"lastName\":\"User\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Email already registered"));
    }


    @Test
    void testLogIn_Success() throws Exception {
        LogInRequest logInRequest = new LogInRequest();
        logInRequest.setUserName("testuser");
        logInRequest.setPassword("password");

        LogInResponse logInResponse = new LogInResponse();
        logInResponse.setToken("mockToken");

        when(customerServiceAuth.authenticateUser(any(LogInRequest.class))).thenReturn(logInResponse);

        mockMvc.perform(post("/api/auth/login")
                        .contentType("application/json")
                        .content("{\"userName\":\"testuser\", \"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mockToken"));
    }

    @Test
    void testLogIn_Failure_InvalidCredentials() throws Exception {
        LogInRequest logInRequest = new LogInRequest();
        logInRequest.setUserName("testuser");
        logInRequest.setPassword("wrongPassword");

        when(customerServiceAuth.authenticateUser(any(LogInRequest.class)))
                .thenThrow(new RuntimeException("Invalid email or password"));

        mockMvc.perform(post("/api/auth/login")
                        .contentType("application/json")
                        .content("{\"userName\":\"testuser\", \"password\":\"wrongPassword\"}"))
                .andExpect(status().isBadRequest());
    }
}
