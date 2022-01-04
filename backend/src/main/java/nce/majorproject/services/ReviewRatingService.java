package nce.majorproject.services;

import nce.majorproject.dto.AddReviewRatingRequest;
import nce.majorproject.dto.IdResponse;
import nce.majorproject.dto.Reviews;
import nce.majorproject.dto.product.RatingResponse;

import java.util.List;

public interface ReviewRatingService {


    IdResponse addReview(AddReviewRatingRequest reviewRequest);
    List<Reviews> listProductReviews(Long productId);
    RatingResponse countAverageRating(Long productId);
    int countTotalReview(Long productId);
}
