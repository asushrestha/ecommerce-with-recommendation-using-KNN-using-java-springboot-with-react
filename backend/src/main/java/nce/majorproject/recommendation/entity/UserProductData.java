package nce.majorproject.recommendation.entity;

import lombok.Getter;
import lombok.Setter;
import nce.majorproject.entities.Product.Product;
import nce.majorproject.entities.User;
import nce.majorproject.recommendation.constants.TypeOfInput;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name="user_product_data")
public class UserProductData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User userId;

    @OneToOne
    @JoinColumn(name = "product_id",referencedColumnName = "id")
    private Product productId;

    @Column(name = "selection_local_date_time")
    private String selectionStamp;

    @Column(name = "selection_choice")
    private TypeOfInput selectionParam;

}
