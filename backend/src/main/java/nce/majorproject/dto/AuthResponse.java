package nce.majorproject.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
public class AuthResponse {

    private String accessToken;
    private Long id;
    private String fullName;
    private String userName;
    private LocalDate dob;
    private String gender;
    private String phone;
    private LocalDateTime addedDate;
}
