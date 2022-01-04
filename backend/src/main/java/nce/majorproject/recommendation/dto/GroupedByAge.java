package nce.majorproject.recommendation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupedByAge {
    private Long lowerAge;
    private Long upperAge;
    private String subSubCategory;
}
