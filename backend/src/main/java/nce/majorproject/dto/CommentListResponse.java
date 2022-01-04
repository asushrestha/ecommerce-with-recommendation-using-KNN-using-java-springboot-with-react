package nce.majorproject.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class CommentListResponse {

    private Long id;
    private String comment;
    private float rating;
    private LocalDateTime date;
    private Long userId;
    private String author;
}
