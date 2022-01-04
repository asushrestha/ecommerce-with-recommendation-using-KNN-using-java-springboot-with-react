package nce.majorproject.recommendation.services;

import nce.majorproject.entities.Product.Product;
import nce.majorproject.recommendation.dto.Request;
import nce.majorproject.recommendation.dto.Response;

import java.util.List;

public interface RecommendationService {

    List<Product> getRecommendation(Request request);
}
