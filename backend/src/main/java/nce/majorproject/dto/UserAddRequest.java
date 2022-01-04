package nce.majorproject.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
public class UserAddRequest {
    @NotBlank(message = "Full Name cannot be blank!!")
    private String fullName;

    @NotBlank(message = "User Name cannot be blank!!")
    private String userName;

    @NotBlank(message = "Address cannot be blank!!")
    private String address;

    @NotNull(message = "DOB cannot be blank")
    private String dob;

    @Size(min =8,max = 50,message = "invalid length!!")
    @NotBlank(message = "Password cannot be blank!!")
    private String password;

    @Size(min = 10,max = 10,message = "invalid phone number length length!!")
    @NotBlank(message = "Phone cannot be blank!!")
    private String phone;

    @NotBlank(message = "Specify Gender")
    private String gender;
}
