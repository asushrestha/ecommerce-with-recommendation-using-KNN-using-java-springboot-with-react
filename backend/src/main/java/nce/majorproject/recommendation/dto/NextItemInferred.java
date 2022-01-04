package nce.majorproject.recommendation.dto;

import lombok.Builder;
import lombok.Getter;
import nce.majorproject.dto.product.LatestAddedProductResponse;

import java.util.List;

@Getter
@Builder
public class NextItemInferred {

    private List<LatestAddedProductResponse> recommendedItemsList;
}
