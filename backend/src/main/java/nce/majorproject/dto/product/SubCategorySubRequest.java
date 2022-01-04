package nce.majorproject.dto.product;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SubCategorySubRequest {

    @NotNull(message = "sub-category id cannot be null")
    private Long subCategorySubId;

    @NotBlank(message = "sub-sub-category-name cannot be blank")
    private String subSubCategoryName;


}
