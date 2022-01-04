package nce.majorproject.recommendation.services;

import nce.majorproject.dto.product.LatestAddedProductResponse;
import nce.majorproject.entities.User;
import nce.majorproject.recommendation.dto.NextItemInferred;
import nce.majorproject.recommendation.dto.UserSelectionRequest;
import nce.majorproject.recommendation.entity.UserProductData;

import java.util.List;

public interface UserTracker {
    NextItemInferred recordUserSelection(UserSelectionRequest request);

    List<LatestAddedProductResponse> hitter();

    UserProductData saveUserMapping(User user, UserSelectionRequest request);
}
