package nce.majorproject.entities;

import lombok.Getter;
import lombok.Setter;
import nce.majorproject.entities.Product.Product;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@Table(name="cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "added_date")
    private LocalDateTime addedDate;
    @OneToOne
    @JoinColumn(name = "product_id",referencedColumnName = "id")
    private Product productId;
    @Column(name = "quantity")
    private int quantity;
    @OneToOne
    @JoinColumn(name="user_id",referencedColumnName="id")
    private User userId;
    @Column(name="isCheckout")
    private boolean isCheckout;
    @Column(name="isRemoved")
    private boolean isRemoved;
    @Column(name="modifiedDate")
    private LocalDateTime modifiedDate;

}
