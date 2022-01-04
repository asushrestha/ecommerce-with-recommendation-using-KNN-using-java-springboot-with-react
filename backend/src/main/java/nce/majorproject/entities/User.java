package nce.majorproject.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "users")
@JsonIgnoreProperties({"password"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "full_name")
    private String fullName;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "address")
    private String address;
    @Column(name = "dob")
    private LocalDate dob;
    @Column(name="gender")
    private String gender;
    @Column(name = "phone")
    private String phone;
    @Column(name = "added_date")
    private LocalDateTime addedDate;
    @JsonIgnore
    @Column(name = "password")
    private String pasword;

    @Column(name = "login_time")
    private LocalDateTime loginTime;
}
