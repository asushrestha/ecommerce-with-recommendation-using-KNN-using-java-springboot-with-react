package nce.majorproject.controller.product;

import lombok.extern.slf4j.Slf4j;
import nce.majorproject.constant.Route;
import nce.majorproject.dto.FilterProduct;
import nce.majorproject.dto.Response;
import nce.majorproject.dto.product.*;
import nce.majorproject.entities.Product.Category;
import nce.majorproject.entities.Product.SubCategory;
import nce.majorproject.entities.Product.SubSubCategory;
import nce.majorproject.services.CategoryService;
import nce.majorproject.services.ProductService;
import nce.majorproject.services.SubCategoryService;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(Route.PRODUCT)
@Slf4j
public class ProductController {
    private ProductService productService;
    private CategoryService categoryService;
    private SubCategoryService subCategoryService;

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService, SubCategoryService subCategoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.subCategoryService = subCategoryService;
    }

    @PostMapping(value = "/add-product")
    public Response addProduct(@Valid@MultipartForm AddRequest request) throws IOException {
        log.info("adding product::{}",request.getName());
        return productService.addProducts(request);
    }
    @PostMapping(value ="/add-category")
    public Response addCategory(@Valid @RequestBody CategoryRequest request){
        log.info("adding category::{}",request.getName());
        return categoryService.addCategory(request);
    }
    @PostMapping(value ="/add-subCategory")
    public Response addSubCategory(@Valid @RequestBody SubCategoryRequest request){
        log.info("adding sub category::{}",request.getName());
        return subCategoryService.addSubCategory(request);
    }
    @PostMapping(value = "add-sub-category/sub-type")
    public Response addSubCategoryTypeSubType(@Valid @RequestBody SubCategorySubRequest request){
        log.info("add sbu category type::");
        return  subCategoryService.addSubCategorySubType(request);
    }
    @GetMapping(value = "/sub-sub-category-list")
    public List<SubSubCategory> listSubSubCategory(){
        log.info("list sub sub category");
        return subCategoryService.listSubSubCategory();
    }
    @GetMapping(value = "/latest-added")
    public List<LatestAddedProductResponse> getLatest(){
        log.info("latest added::");
        return productService.showLatestAdded();
    }
    @GetMapping(value = "/list-category")
    public List<Category> listCategory(){
        return categoryService.listCategory();
    }

    @GetMapping(value = "/list-sub-category")
    public List<SubCategory> listSubCategory(){
        return subCategoryService.listSubCategory();
    }

    @GetMapping(value = "/get-product-by-id")
    public LatestAddedProductResponse getProductById(@RequestParam Long id){
        log.info("get product by id::",id);
        return productService.getProductById(id);
    }
    @PostMapping(value = "/filter")
    public List<LatestAddedProductResponse> filter(@Valid@RequestBody FilterProduct filterProduct){
        return  productService.filter(filterProduct);
    }

    @GetMapping(value = "/top-5" )
    public List<LatestAddedProductResponse> topFiveProduct(){
        return  productService.getTopFive();
    }
}
