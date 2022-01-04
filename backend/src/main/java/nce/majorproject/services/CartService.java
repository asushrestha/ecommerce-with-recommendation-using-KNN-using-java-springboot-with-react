package nce.majorproject.services;



import nce.majorproject.dto.Response;
import nce.majorproject.dto.cart.*;
import nce.majorproject.dto.product.LatestAddedProductResponse;
import nce.majorproject.entities.Cart;

import java.util.List;

public interface CartService {

    List<ShowInCartById> showCart();

    Response addToCart(CartAdd addInCart);

    Response removeFromCart(CartRemove removeInCart);

    Response removeAllFromCart();

    List<ShowInCartById> findPopularProducts();

    Response checkOutAllCart();

    Response checkOutByCartId(Long cartId);

    List<Cart> listCheckout();
}
