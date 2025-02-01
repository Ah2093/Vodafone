package eg.com.vodafone.controller;

import eg.com.vodafone.service.CustomerServiceAuth;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class ViewController {

    private final CustomerServiceAuth customerServiceAuth;

    public ViewController(CustomerServiceAuth customerServiceAuth) {
        this.customerServiceAuth = customerServiceAuth;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }


    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @GetMapping("/welcome")
    public String showWelcomePage(@AuthenticationPrincipal UserDetails userDetails) {
        return "welcome";
    }
}