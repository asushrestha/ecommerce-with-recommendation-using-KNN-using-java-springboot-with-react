package nce.majorproject.recommendation.services.impl;

import nce.majorproject.entities.Product.Product;
import nce.majorproject.recommendation.dto.Request;
import nce.majorproject.recommendation.dto.Response;
import nce.majorproject.recommendation.repository.RecommendationRepository;
import nce.majorproject.recommendation.services.RecommendationService;
import nce.majorproject.recommendation.services.validator.ProductValidator;
import nce.majorproject.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RecommendationServiceImpl implements RecommendationService {

    private ProductService productService;
    private RecommendationRepository recommendationRepository;
    private ProductValidator productValidator;
    @Autowired
    public RecommendationServiceImpl(ProductService productService,
                                     RecommendationRepository recommendationRepository){
        this.productService = productService;
        this.recommendationRepository = recommendationRepository;
    }
    @Override
    public List<Product> getRecommendation(Request request) {

        return fetchRecommendedData(request);
    }

    private List<Product> fetchRecommendedData(Request request){

        Product validProduct = productValidator.validateProduct(request.getCurrentSelectionProductId());
        //store user pick for user-item recommendation:
        //save data:
        return  recommendationRepository.findByCurrentInputData(validProduct);
    }
}
