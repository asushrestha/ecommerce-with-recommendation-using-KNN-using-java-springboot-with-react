package nce.majorproject.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class AddReviewRatingRequest {


    private String review;

    private int rating;

    @NotNull(message = "Review Done to cannot be null!!")
    private Long reviewDoneTo;
}
