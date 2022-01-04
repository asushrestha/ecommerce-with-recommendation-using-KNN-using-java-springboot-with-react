package nce.majorproject.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name="admins")
@JsonIgnoreProperties({"password"})
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "user_name")
    private String userName;

    @Column(name="phone")
    private String phone;

    @Column(name="password")
    private String password;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "added_date")
    private LocalDateTime addedDate;

}
