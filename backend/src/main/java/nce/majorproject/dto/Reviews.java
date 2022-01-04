package nce.majorproject.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Reviews {

    private Long reviewId;
    private Long userId;
    private String userName;
    private String review;
    private int rating;
    private LocalDateTime addedDate;
    private Long productId;
}
