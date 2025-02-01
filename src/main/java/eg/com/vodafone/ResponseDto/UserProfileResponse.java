package eg.com.vodafone.ResponseDto;

import lombok.Data;

@Data
public class UserProfileResponse {
    private String userName;
    private String email;
    private String firstName;
    private String lastName;
}