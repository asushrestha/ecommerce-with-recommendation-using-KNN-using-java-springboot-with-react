package nce.majorproject.recommendation.entity;

import lombok.Getter;
import lombok.Setter;
import nce.majorproject.entities.Product.Product;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "recommendation_current_selection")
public class RecommendationBasedOnCurrentSelection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "product",referencedColumnName = "id")
    private Product product;

    @OneToOne
    @JoinColumn(name = "next_product",referencedColumnName = "id")
    private Product nextProduct;

    @Column(name = "similarity_score")
    private Long similarityScore;

}
