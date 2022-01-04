package nce.majorproject.services.impl;

import nce.majorproject.context.ContextHolderServices;
import nce.majorproject.dto.Response;
import nce.majorproject.dto.product.SubCategoryRequest;
import nce.majorproject.dto.product.SubCategorySubRequest;
import nce.majorproject.entities.Product.Category;
import nce.majorproject.entities.Product.SubCategory;
import nce.majorproject.entities.Product.SubSubCategory;
import nce.majorproject.exception.RestException;
import nce.majorproject.repositories.product.SubCategoryRepository;
import nce.majorproject.repositories.product.SubSubCategoryRepository;
import nce.majorproject.services.CategoryService;
import nce.majorproject.services.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SubCategoryServiceImpl  implements SubCategoryService {
  private SubCategoryRepository subCategoryRepository;
  private ContextHolderServices contextHolderServices;
  private CategoryService categoryService;
  private SubSubCategoryRepository subSubCategoryRepository;
    @Autowired
    public SubCategoryServiceImpl(SubCategoryRepository subCategoryRepository,
                                  CategoryService categoryService,
                                  ContextHolderServices contextHolderServices,
                                  SubSubCategoryRepository subSubCategoryRepository) {
        this.subCategoryRepository = subCategoryRepository;
        this.contextHolderServices = contextHolderServices;
        this.categoryService=categoryService;
        this.subSubCategoryRepository = subSubCategoryRepository;
    }

    @Override
    public Boolean validateSubSUbCategoryByName(String value){
        return subSubCategoryRepository.findByName(value).isPresent();
    }
    @Override
    public SubCategory validateSubCategoryById(Long id) {
        Optional<SubCategory> optionalSubCategory=subCategoryRepository.validateSubCategoryById(id);
        SubCategory subCategory=optionalSubCategory.orElseThrow(()->new RestException("invalid sub category!!"));
        return subCategory;
    }

    @Override
    public Response addSubCategory(SubCategoryRequest request) {
        SubCategory subCategory=prepareSubCategoryAddData(request);
        SubCategory response=subCategoryRepository.save(subCategory);
        return Response.builder().id(response.getId()).status("Subcategory added successfully").build();
    }

    @Override
    public List<SubCategory> listSubCategory() {
        return this.subCategoryRepository.findAll();
    }

    @Override
    public Response addSubCategorySubType(SubCategorySubRequest request) {

        SubSubCategory response = this.subSubCategoryRepository.save(prepareSubSubCategoryAdd(request));
        return Response
                .builder()
                .id(response.getId())
                .status("SubSubCategory Added Successfully")
                .build();
    }

    @Override
    public List<SubSubCategory> listSubSubCategory(){
        return  this.subSubCategoryRepository.findAll();
    }

    @Override
    public SubSubCategory validateSubSubCategoryId(Long id){
        return  this.subSubCategoryRepository.findById(id)
                .orElseThrow(()->new RestException("Invalid subsubcategory"));
    }
    private SubSubCategory prepareSubSubCategoryAdd(SubCategorySubRequest request){

        SubSubCategory subSubCategory = new SubSubCategory();

        subSubCategory.setAddedBy(contextHolderServices.getContext().getFullName());
        subSubCategory.setAddedDate(LocalDateTime.now());
        subSubCategory.setName(request.getSubSubCategoryName());
        subSubCategory.setSubCategory(subCategoryRepository
                .findById(request.getSubCategorySubId()).orElseThrow(()->new RestException("invalid subcategory")));

        return  subSubCategory;
    }

    private SubCategory prepareSubCategoryAddData(SubCategoryRequest request){
        SubCategory subCategory=new SubCategory();
        Category category=categoryService.validateCategoryId(request.getCategoryId());
        subCategory.setAddedBy(contextHolderServices.getContext().getFullName());
        subCategory.setAddedDate(LocalDateTime.now());
        subCategory.setName(request.getName());
        subCategory.setCategory(category);
        return subCategory;
    }
}
