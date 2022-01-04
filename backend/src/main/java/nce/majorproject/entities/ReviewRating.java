package nce.majorproject.entities;

import lombok.Getter;
import lombok.Setter;
import nce.majorproject.entities.Product.Product;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "reviews_ratings")
public class ReviewRating {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "added_date")
    private LocalDateTime addedDate;

    @Column(name = "content")
    private String content;

    @Column(name = "rating")
    private int rating;

    @OneToOne
    @JoinColumn(name = "review_doneby",referencedColumnName = "id")
    private User reviewDoneBy;

    @OneToOne
    @JoinColumn(name = "review_doneOn",referencedColumnName = "id")
    private Product reviewDoneOn;

}
