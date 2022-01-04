package nce.majorproject.recommendation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nce.majorproject.entities.Product.SubSubCategory;

@Getter
@Setter
@AllArgsConstructor
public class SubCategoryAndCountRecommender {
private SubSubCategory subSubCategory;
private Long count;

    public SubCategoryAndCountRecommender(SubSubCategory subSubCategory, Long count) {
        this.subSubCategory = subSubCategory;
        this.count = count;
    }

    private boolean isApproved;
}
