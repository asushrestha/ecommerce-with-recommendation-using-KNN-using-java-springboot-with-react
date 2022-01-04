package nce.majorproject.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class AuthRequest {
    @NotBlank(message = "User Name cannot be null")
    private String userName;
    @Size(min =5,max = 50,message = "Invalid password length!!")
    @NotBlank(message = "Password cannot be null")
    private String password;
}
