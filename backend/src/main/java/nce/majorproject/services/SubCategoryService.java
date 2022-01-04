package nce.majorproject.services;

import nce.majorproject.dto.Response;
import nce.majorproject.dto.product.SubCategoryRequest;
import nce.majorproject.dto.product.SubCategorySubRequest;
import nce.majorproject.entities.Product.SubCategory;
import nce.majorproject.entities.Product.SubSubCategory;

import java.util.List;

public interface SubCategoryService {

    Boolean validateSubSUbCategoryByName(String value);

    SubCategory validateSubCategoryById(Long id);
    Response addSubCategory(SubCategoryRequest request);
    List<SubCategory> listSubCategory();
    Response addSubCategorySubType(SubCategorySubRequest request);

    List<SubSubCategory> listSubSubCategory();

    SubSubCategory validateSubSubCategoryId(Long id);
}
