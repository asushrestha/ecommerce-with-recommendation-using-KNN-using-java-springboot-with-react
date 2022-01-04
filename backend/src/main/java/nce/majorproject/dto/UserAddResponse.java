package nce.majorproject.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class UserAddResponse {
    private String accessToken;
    private Long id;
    private String fullName;
    private String userName;
    private String address;
    private LocalDate dob;
    private String gender;
    private String phone;
    private LocalDateTime addedDate;
    private LocalDateTime loginTime;
}
