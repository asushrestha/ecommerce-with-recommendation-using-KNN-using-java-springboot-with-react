package nce.majorproject.repositories;

import lombok.Getter;
import nce.majorproject.entities.Product.Product;
import nce.majorproject.entities.ReviewRating;
import nce.majorproject.entities.User;
import nce.majorproject.recommendation.entity.DataSetReferer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRatingRepository extends JpaRepository<ReviewRating,Long> {

    @Query(value = "select r from ReviewRating r where r.reviewDoneBy=?1 and r.reviewDoneOn=?2")
    Optional<ReviewRating> findByUserAndProduct(User reviewDoneBy, Product reviewDoneOn);

    @Query(value = "select r from  ReviewRating  r where r.reviewDoneOn=?1")
    List<ReviewRating> findProductReviews(Product reviewDoneOn);

    @Query(value = "select sum(r.rating)/count(r.id) FROM ReviewRating  r where (r.rating IS NOT NULL OR r.rating <> 0) and r.reviewDoneOn=?1")
    float findNoOfRatings(Product product);

    @Query(value = "select count(r.id) from ReviewRating r where r.reviewDoneOn=?1")
    int countTotalReviews(Product product);

    @Query(value = "select count(r.id) from ReviewRating r where r.reviewDoneBy=?1")
    int findNoOfRatingMadeByUser(User user);

    @Query(value = "select r from ReviewRating  r where r.reviewDoneBy=?1")
    List<ReviewRating> findByUserRatedProduct(User user);
}
