package nce.majorproject.recommendation.services.validator;

import nce.majorproject.entities.Product.Product;
import nce.majorproject.exception.RestException;
import nce.majorproject.repositories.product.ProductRepository;
import nce.majorproject.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductValidatorImpl implements ProductValidator {

    private ProductRepository productRepository;
    @Autowired
    public ProductValidatorImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }
    @Override
    public Product validateProduct(Long id) {
        return productRepository.validateProductById(id)
                .orElseThrow(() -> new RestException("Not a valid product Id"));
    }
}
