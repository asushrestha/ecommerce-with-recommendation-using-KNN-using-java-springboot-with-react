package nce.majorproject.entities.Product;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@Table(name = "sub_sub_categories")
public class SubSubCategory {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "sub_sub_category_name")
        private String name;

        @Column(name = "added_by")
        private String addedBy;

        @Column(name = "added_date")
        private LocalDateTime addedDate;

        @OneToOne
        @JoinColumn(name = "sub_category_id",referencedColumnName = "id")
        private SubCategory subCategory;
}
