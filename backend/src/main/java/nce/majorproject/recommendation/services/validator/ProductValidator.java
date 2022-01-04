package nce.majorproject.recommendation.services.validator;

import nce.majorproject.entities.Product.Product;

public interface ProductValidator {
    Product validateProduct(Long id);
}
