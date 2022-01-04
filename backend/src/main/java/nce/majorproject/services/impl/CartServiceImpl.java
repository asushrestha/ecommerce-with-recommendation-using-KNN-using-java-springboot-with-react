package nce.majorproject.services.impl;

import nce.majorproject.context.ContextHolderServices;
import nce.majorproject.dto.Response;
import nce.majorproject.dto.cart.*;
import nce.majorproject.entities.Cart;
import nce.majorproject.entities.Product.Product;
import nce.majorproject.entities.User;
import nce.majorproject.exception.RestException;
import nce.majorproject.recommendation.constants.TypeOfInput;
import nce.majorproject.recommendation.dto.UserSelectionRequest;
import nce.majorproject.recommendation.services.UserTracker;
import nce.majorproject.repositories.CartRepository;
import nce.majorproject.services.CartService;
import nce.majorproject.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    private CartRepository cartRepository;
    private UserServiceImpl userService;
    private ProductServiceImpl productService;
    private ContextHolderServices contextHolderServices;
    private UserTracker userTracker;
    @Autowired
    public CartServiceImpl(CartRepository cartRepository,
                           ProductServiceImpl productService,
                           UserServiceImpl userService,
                           ContextHolderServices contextHolderServices, UserTracker userTracker){
        this.cartRepository=cartRepository;
        this.userService=userService;
        this.productService=productService;
        this.contextHolderServices = contextHolderServices;
        this.userTracker = userTracker;
    }
    @Override
    public List<ShowInCartById> showCart() {
        User user=userService.validateUser(contextHolderServices.getContext().getId());//validation
        List<Cart> getCart=cartRepository.findCartById(user);

        List<ShowInCartById> response=new ArrayList<>();
        getCart.forEach(itemInCart ->{
                    ShowInCartById showInCartResponse=setToShowInCart(itemInCart);
                    response.add(showInCartResponse);
                });
        System.out.println(response);
        return response;
    }

    private ShowInCartById setToShowInCart(Cart cart) {
        ShowInCartById request=new ShowInCartById();
        request.setCartid(cart.getId());
        request.setQuantity(cart.getQuantity());
        request.setPrice(cart.getProductId().getPrice());
        request.setCategory(cart.getProductId().getCategory().getName());
        request.setAddedDate(cart.getAddedDate());
        request.setPhoto(ImageUtil.decompressBytes(cart.getProductId().getPhoto()));
        request.setSubCategory(cart.getProductId().getSubCategory().getName());
        request.setProduct_id(cart.getProductId().getId());
        request.setProductName(cart.getProductId().getProductName());
        return request;
    }

    @Override
    public Response addToCart(CartAdd addInCart) {
        Cart addCart=addDataInCart(addInCart);
        Cart response=cartRepository.save(addCart);
        return Response.builder().id(response.getId()).status("Item added to cart successfully").build();
    }

    private Cart addDataInCart(CartAdd cart) {
        Cart response=new Cart();
        Product product=productService.validateProduct(cart.getProduct_id());

        User user=userService.validateUser(contextHolderServices.getContext().getId());
        response.setAddedDate(LocalDateTime.now());
        response.setModifiedDate(LocalDateTime.now());
        response.setCheckout(false);
        response.setRemoved(false);
        response.setQuantity(cart.getQuantity());
        response.setProductId(product);
        response.setUserId(user);
        return response;
    }

    @Override
    public Response removeFromCart(CartRemove removeInCart) {
        User user=userService.validateUser(contextHolderServices.getContext().getId());//validate
        cartRepository.removeFromCartDB(user,removeInCart.getCartid());
        return Response.builder().status("SUCCESS").build();
    }

    @Override
    public Response removeAllFromCart() {
     User user = userService.validateUser(contextHolderServices.getContext().getId());
     cartRepository.removeAllFromCartDB(user);
     return Response.builder().status("SUCCESS").build();
    }

    @Override
    public List<ShowInCartById> findPopularProducts() {
        List<Cart> cartList=cartRepository.findPopular();
        List<ShowInCartById> response=new ArrayList<>();
        cartList.forEach(itemInCart ->{
                    ShowInCartById showInCartResponse=setToShowInCart(itemInCart);
                    response.add(showInCartResponse);
                }
        );
        return response;
    }

//    public static void main(String[] args) {
//        System.out.println(LocalDateTime.now());
//    }
    @Override
    public Response checkOutAllCart() {

        User user = userService.validateUser(contextHolderServices.getContext().getId());
        List<Cart> cartList = this.cartRepository.findCartById(user);
        System.out.println(cartList.size());
        mapInRecommender(cartList,user);
        this.cartRepository.checkOutFromCart(user,LocalDateTime.now());
        return Response.builder().id(user.getId()).build();
    }

    private void mapInRecommender(List<Cart> cartList, User user) {
        cartList.forEach(cart -> {
            userTracker.saveUserMapping(user,prepareUserSelectionRequest(cart));
            System.out.println("aaa");
        });
        System.out.println("aayo");
    }

    private UserSelectionRequest prepareUserSelectionRequest(Cart cart){
        UserSelectionRequest request = new UserSelectionRequest();
        request.setProductId(cart.getProductId().getId());
        request.setSelectionParam(TypeOfInput.CHECKOUT.name());
        LocalDateTime nowTime =LocalDateTime.now();
        request.setLocalDateTime(String.valueOf(nowTime).split("T")[0] + " "+String.valueOf(nowTime).split("T")[1]);
        return  request;
    }

    @Override
    public Response checkOutByCartId(Long cartId) {

        User user = userService.validateUser(contextHolderServices.getContext().getId());

        cartRepository.findById(cartId).orElseThrow(()->new RestException("Invalid Cart id"));

        this.cartRepository.checkOutFromCart(user,cartId);

        return Response.builder().id(cartId).build();
    }

    @Override
    public List<Cart> listCheckout() {
        User validUser = userService.validateUser(contextHolderServices.getContext().getId());
        return cartRepository.findCheckoutProduct(validUser);
    }


}
