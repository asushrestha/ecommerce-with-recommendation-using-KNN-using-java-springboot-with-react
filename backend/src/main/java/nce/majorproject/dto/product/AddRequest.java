package nce.majorproject.dto.product;

import lombok.Getter;
import lombok.Setter;
import org.jboss.resteasy.annotations.providers.multipart.PartType;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.ws.rs.FormParam;

@Getter
@Setter
public class AddRequest {

    @NotBlank(message = "Name is mandatory")
    @FormParam("Name")
    @PartType("text/plain")
    private String name;

    @FormParam("colour")
    @PartType("text/plain")
    private String colour;

    @NotNull(message = "Price is mandatory")
    @FormParam("price")
    @PartType("text/plain")
    private double price;

    @NotNull(message = "Category Id is mandatory")
    @FormParam("categoryId")
    @PartType("text/plain")
    private Long categoryId;


    @NotNull(message = "sub-sub-category cannot be null")
    @FormParam("subSubCategoryId")
    @PartType("text/plain")
    private Long subSubCategoryId;

    @NotNull(message = "Sub Category Id is mandatory")
    @FormParam("subCategoryId")
    @PartType("text/plain")
    private Long subCategoryId;

    @FormParam("productImage")
    @PartType("image/jpg")
    private MultipartFile productImage;

    @NotNull(message = "quantity is mandatory")
    @FormParam("quantity")
    @PartType("text/plain")
    private int quantity;
}
