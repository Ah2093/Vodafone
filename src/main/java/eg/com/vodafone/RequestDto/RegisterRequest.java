package eg.com.vodafone.RequestDto;

import eg.com.vodafone.validation.PhoneNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "userName is required")
    @Size(min = 3, max = 25, message = "Invalid userName, must be between 3 and 20 characters long")
    private String userName;

    @NotBlank(message = "phoneNumber is required")
    @PhoneNumber
    private String phoneNumber;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email(message = "Invalid email")
    @NotBlank(message = "email is required")
    private String email;

    @NotBlank(message = "password is required")
    @Size(min = 8, message = "Invalid password, must be at least 8 characters long")
    private String password;
}