package nce.majorproject.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
public class AdminRegisterRequest {

    private Long id;

    @NotBlank(message = "UserName cannot be blank!!")
    private String userName;

    private String fullName;

    @Size(min=8,max = 50,message = "Invalid Password length!!")
    @NotBlank(message = "Password cannot be blank!!")
    private String password;

    @Size(min=10,max=10,message = "Invalid Phone no!!")
    @NotBlank(message = "Phone cannot be blank!!")
    private String phone;

    private LocalDate dob;
}
