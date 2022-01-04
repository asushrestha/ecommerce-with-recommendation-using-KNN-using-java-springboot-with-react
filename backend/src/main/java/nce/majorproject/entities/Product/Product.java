package nce.majorproject.entities.Product;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_colour")
    private String colour;
    @Column(name = "price")
    private double price;
    @Column(name = "added_date")
    private LocalDateTime addedDate;
    @OneToOne
    @JoinColumn(name = "category_id",referencedColumnName = "id")
    private Category category;
    @OneToOne
    @JoinColumn(name ="subcategory_id",referencedColumnName = "id")
    private SubCategory subCategory;

    @OneToOne
    @JoinColumn(name = "sub_sub_category_id",referencedColumnName = "id")
    private SubSubCategory subSubCategory;

    @Column(name = "added_by")
    private String addedBy;
    @Column(name = "quantity")
    private int quantity;
    @Column(name="photo",length = 1000)
    private byte[] photo;

}
