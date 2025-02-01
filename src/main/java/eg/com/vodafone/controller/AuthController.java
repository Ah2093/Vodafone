package eg.com.vodafone.controller;


import eg.com.vodafone.dto.LogInRequest;
import eg.com.vodafone.dto.RegisterRequest;
import eg.com.vodafone.dto.LogInResponse;
import eg.com.vodafone.dto.RegisterResponse;
import eg.com.vodafone.service.CustomerServiceAuth;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/auth/")
public class AuthController {

    @Autowired
    private CustomerServiceAuth customerServiceAuth;

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        RegisterResponse registerResponse = customerServiceAuth.registerUser(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(registerResponse);
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<LogInResponse> logIn(@Valid @RequestBody LogInRequest logInRequest) {
        LogInResponse logInResponse = customerServiceAuth.authenticateUser(logInRequest);
        return ResponseEntity.ok(logInResponse);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

}
