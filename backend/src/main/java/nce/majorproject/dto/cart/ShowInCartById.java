package nce.majorproject.dto.cart;

import lombok.Getter;
import lombok.Setter;
import nce.majorproject.entities.Product.Category;
import nce.majorproject.entities.Product.SubCategory;

import java.time.LocalDateTime;

@Getter
@Setter
public class ShowInCartById {
    private Long product_id;
    private LocalDateTime addedDate;
    private int quantity;
    private String productName;
    private double price;
    private String category;
    private String subCategory;
    private byte[] photo;
    private Long cartid;
}
