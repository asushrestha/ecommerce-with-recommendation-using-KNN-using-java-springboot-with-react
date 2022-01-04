package nce.majorproject.services.impl;

import nce.majorproject.context.ContextHolderServices;
import nce.majorproject.dto.Response;
import nce.majorproject.dto.product.CategoryRequest;
import nce.majorproject.entities.Product.Category;
import nce.majorproject.exception.RestException;
import nce.majorproject.repositories.product.CategoryRepository;
import nce.majorproject.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;
    private ContextHolderServices contextHolderServices;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               ContextHolderServices contextHolderServices) {
        this.categoryRepository = categoryRepository;
        this.contextHolderServices = contextHolderServices;
    }

    @Override
    public Category validateCategoryId(Long id) {
        Optional<Category> optionalCategory=categoryRepository.validateById(id);
        Category category=optionalCategory.orElseThrow(()->new RestException("invalid category id"));
        return category;
    }

    @Override
    public Response addCategory(CategoryRequest request) {
       Category category=prepareToAddCategory(request);
       Category response=categoryRepository.save(category);
       return Response.builder().id(response.getId()).status("Category Added Successfully").build();
    }

    @Override
    public List<Category> listCategory() {
        return this.categoryRepository.findAll();
    }

    private Category prepareToAddCategory(CategoryRequest request){
        Category category=new Category();
        category.setAddedBy("contextHolderServices.getContext().getFullName()");
        category.setAddedDate(LocalDateTime.now());
        category.setName(request.getName());
        return category;
    }
}
