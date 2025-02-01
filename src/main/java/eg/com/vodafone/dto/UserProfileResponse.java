package eg.com.vodafone.dto;

import lombok.Data;

@Data
public class UserProfileResponse {
    private String userName;
    private String email;
    private String firstName;
    private String lastName;
}