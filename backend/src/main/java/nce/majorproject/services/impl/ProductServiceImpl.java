package nce.majorproject.services.impl;

import nce.majorproject.context.ContextHolderServices;
import nce.majorproject.dto.FilterProduct;
import nce.majorproject.dto.Response;
import nce.majorproject.dto.product.AddRequest;
import nce.majorproject.dto.product.LatestAddedProductResponse;
import nce.majorproject.entities.Product.Category;
import nce.majorproject.entities.Product.Product;
import nce.majorproject.entities.Product.SubCategory;
import nce.majorproject.entities.User;
import nce.majorproject.exception.RestException;
import nce.majorproject.repositories.product.CategoryRepository;
import nce.majorproject.repositories.product.ProductRepository;
import nce.majorproject.repositories.product.SubCategoryRepository;
import nce.majorproject.services.*;
import nce.majorproject.util.ImageUtil;
import nce.majorproject.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl  implements ProductService {
    private ProductRepository productRepository;
    private CategoryService categoryService;
    private SubCategoryService subCategoryService;
    private ContextHolderServices contextHolderServices;
    private CommentService commentService;
    private ReviewRatingService reviewRatingService;
    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryService categoryService,
                              SubCategoryService subCategoryService,
                              ContextHolderServices contextHolderServices,
                              @Lazy CommentService commentService,
                              @Lazy ReviewRatingService reviewRatingService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.subCategoryService = subCategoryService;
        this.contextHolderServices = contextHolderServices;
        this.commentService = commentService;
        this.reviewRatingService = reviewRatingService;
    }

    @Override
    public Response addProducts(AddRequest request) throws IOException {
        //System.out.println(contextHolderServices.getContext().getFullName());
        Product product=prepareToAddProduct(request);
        Product response=productRepository.save(product);
        return Response.builder().id(response.getId()).status("Product Added Successfully").build();
    }

    @Override
    public List<LatestAddedProductResponse> showLatestAdded() {
        List<Product> productList=productRepository.findLatestAddedProduct();
        List<LatestAddedProductResponse> productResponseList=new ArrayList<>();
        productList.forEach(latestAddedProductResponse -> {
            LatestAddedProductResponse productResponse=prepareToShowLatestAddedProduct(latestAddedProductResponse);
            productResponseList.add(productResponse);
            System.out.println(productResponse);
        });
        return productResponseList;
    }

    @Override
    public Product validateProduct(Long id) {
        Optional<Product> product=productRepository.findById(id);
        return product.orElseThrow(()->new RestException("Product not found."));
    }

    @Override
    public List<LatestAddedProductResponse> randomProduct() {
        List<Product> productList=productRepository.randomProduct();
        List<LatestAddedProductResponse> productResponseList=new ArrayList<>();
        //todo randomize product
        productList.forEach(latestAddedProductResponse -> {
            LatestAddedProductResponse productResponse=prepareToShowLatestAddedProduct(latestAddedProductResponse);
            productResponseList.add(productResponse);
            System.out.println(productResponse);
        });
        return productResponseList;
    }

    @Override
    public LatestAddedProductResponse getProductById(Long id) {
        Product product = validateProduct(id);
        return prepareToShowLatestAddedProduct(product);
    }

    @Override
    public List<LatestAddedProductResponse> filter(FilterProduct filter) {
        List<LatestAddedProductResponse> filterList = new ArrayList<>();
        List<Product> filteredProducts = new ArrayList<>();
        if(filter.getSubCategory()!=null) {
           filteredProducts = this.productRepository.filter(filter.getCategory(),filter.getSubCategory());
        }else{
            filteredProducts = this.productRepository.filterCat(filter.getCategory());
        }
        filteredProducts.forEach((product)->{
            LatestAddedProductResponse filteredData = prepareToShowLatestAddedProduct(product);
            filterList.add(filteredData);
        });
       return filterList;
    }

    @Override
    public List<LatestAddedProductResponse> getTopFive() {
        Pageable pageable = PaginationUtil.performPagination(1,5);
        List<Product> filteredProducts = this.productRepository.getTopFive(pageable);
        List<LatestAddedProductResponse> filterList = new ArrayList<>();
        filteredProducts.forEach((product)->{
            LatestAddedProductResponse filteredData = prepareToShowLatestAddedProduct(product);
            filterList.add(filteredData);
        });
        return filterList;
    }

    private LatestAddedProductResponse prepareToShowLatestAddedProduct(Product product) {
        LatestAddedProductResponse response = new LatestAddedProductResponse();
        response.setId(product.getId());
        response.setName(product.getProductName());
        response.setPrice(product.getPrice());
        response.setQuantity(product.getQuantity());
        response.setImg(ImageUtil.decompressBytes(product.getPhoto()));
        response.setCompany(product.getSubCategory().getName());
        response.setInfo(product.getSubCategory().getName());
        response.setCount("0");
        response.setInCart("false");
        response.setTotal("0");
        response.setCategory(product.getCategory().getName());
        response.setSubSubCategory(product.getSubSubCategory().getName());
        response.setRating(reviewRatingService.countAverageRating(product.getId()));
        response.setComment(commentService.getAllCommentFromPostId(product.getId()));
        return response;
    }

    private Product prepareToAddProduct(AddRequest request) throws IOException {
       byte[] image=request.getProductImage().getBytes();
        Product product=new Product();
        product.setAddedBy(this.contextHolderServices.getContext().getUserType());
        product.setAddedDate(LocalDateTime.now());
        Category category=categoryService.validateCategoryId(request.getCategoryId());
        SubCategory subCategory=subCategoryService.validateSubCategoryById(request.getSubCategoryId());;
        product.setSubCategory(subCategory);
        product.setCategory(category);
        product.setColour(request.getColour());
        product.setPrice(request.getPrice());
        product.setPhoto(ImageUtil.compressBytes(image));
        product.setQuantity(request.getQuantity());
        product.setSubSubCategory(subCategoryService.validateSubSubCategoryId(request.getSubSubCategoryId()));
        product.setProductName(request.getName());
        return product;
    }

}
