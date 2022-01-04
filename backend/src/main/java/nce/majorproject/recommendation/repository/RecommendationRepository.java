package nce.majorproject.recommendation.repository;

import nce.majorproject.entities.Product.Product;
import nce.majorproject.recommendation.entity.RecommendationBasedOnCurrentSelection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendationRepository extends JpaRepository<RecommendationBasedOnCurrentSelection,Long> {

    @Query(value = "select product.nextProduct from RecommendationBasedOnCurrentSelection product where product.product=:product")
    List<Product>findByCurrentInputData(Product product);
}
