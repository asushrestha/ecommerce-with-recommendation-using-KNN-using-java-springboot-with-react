package nce.majorproject.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RatingResponse {
    private Long productId;
    private float rating;
    private float ratingCount;
}
