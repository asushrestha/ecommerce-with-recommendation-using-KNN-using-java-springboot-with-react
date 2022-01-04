package nce.majorproject.recommendation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSelectionRequest {
    private Long productId;
    private String localDateTime;
    private String selectionParam;
}
