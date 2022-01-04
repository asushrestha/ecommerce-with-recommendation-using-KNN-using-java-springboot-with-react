package nce.majorproject.repositories;

import nce.majorproject.entities.Cart;
import nce.majorproject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long>{

        @Query(value = "select c from Cart c where c.userId=:userId and c.isRemoved=false and c.isCheckout=false ")
        List<Cart> findCartById(User userId);

        @Modifying
        @Transactional
        @Query(value="update Cart set isRemoved=true where userId=?1 and id=?2")
        void removeFromCartDB(User userId, Long id);

        @Modifying
        @Transactional
        @Query(value="update Cart set isRemoved=true where userId=?1 ")
        void removeAllFromCartDB(User userId);

        @Query(value = "select c from Cart c where c.isCheckout=false and count(c.productId)>5 ")
        List<Cart> findPopular();

        @Modifying
        @Transactional
        @Query(value = "update Cart set isCheckout=true, modifiedDate=:nowDate where userId=:user and isRemoved=false")
        void checkOutFromCart(User user,LocalDateTime nowDate);

        @Modifying
        @Transactional
        @Query(value = "update Cart set isCheckout=true where userId=:user and isRemoved=false and id=:cartId")
        void checkOutFromCart(User user,Long cartId);

        @Query(value = "select cart from Cart cart where cart.userId=:validUser and cart.isRemoved=false and cart.isCheckout=true")
        List<Cart> findCheckoutProduct(User validUser);

        @Query(value = "select count(cart) from Cart cart where cart.isCheckout=true and cart.modifiedDate between :startDate and :nowDate")
        Double findCheckedOutToday(LocalDateTime startDate,LocalDateTime nowDate);

        @Query(value = "select sum(cart.productId.price) from Cart cart where cart.isCheckout=true and cart.modifiedDate between :startDate and :nowDate")
        Double findTransactionToday(LocalDateTime startDate,LocalDateTime nowDate);

//        @Query(value = "select cart from Cart cart where cart.isCheckout=false and cart.isRemoved=false")
//        List<Cart> findCheckOutFromCart(User user, LocalDateTime nowDate);
}