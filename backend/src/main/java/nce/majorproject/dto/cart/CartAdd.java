package nce.majorproject.dto.cart;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CartAdd {
    private Long product_id;
    private int quantity;
    private boolean isCheckout;
    private boolean isRemoved;
    private Long user_id;
    private LocalDateTime addedDate;
    private LocalDateTime modifiedDate;
}
