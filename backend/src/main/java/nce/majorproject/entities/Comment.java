package nce.majorproject.entities;

import lombok.Getter;
import lombok.Setter;
import nce.majorproject.entities.Product.Product;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "added_date")
    private LocalDateTime addedDate;

    @Column(name = "post_comment")
    private String postComment;

    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User userId;

    @OneToOne
    @JoinColumn(name = "product_id",referencedColumnName = "id")
    private Product productId;

    @Column(name = "is_disabled")
    private boolean isDisabled;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    @Column(name = "modifiedBy")
    private String modifiedBy;
}
