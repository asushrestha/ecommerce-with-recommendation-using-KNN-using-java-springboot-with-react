package nce.majorproject.entities.Product;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "category_name")
    private String name;

    @Column(name = "addedBy")
    private String addedBy;

    @Column(name="added_date")
    private LocalDateTime addedDate;

}
