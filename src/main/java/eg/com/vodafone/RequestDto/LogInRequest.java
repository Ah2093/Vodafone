package eg.com.vodafone.RequestDto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogInRequest {

    @NotBlank(message = "userName is required")
    private String userName;
    @NotBlank(message = "Password is required")
    private String password;
}