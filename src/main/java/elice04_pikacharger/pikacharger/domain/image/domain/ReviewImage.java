package elice04_pikacharger.pikacharger.domain.image.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import elice04_pikacharger.pikacharger.domain.common.global.BaseEntity;
import elice04_pikacharger.pikacharger.domain.review.domain.Review;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "review_image")
public class ReviewImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "review_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private Review review;

    @Column(name = "img_url", nullable = false)
    private String imageUrl;

    public ReviewImage(String imageUrl, Review review) {
        this.imageUrl = imageUrl;
        this.review = review;
    }

    @Builder
    public ReviewImage(Review review, String imageUrl){
        this.review = review;
        this.imageUrl = imageUrl;
    }

    public String getImageUrl(){
        return this.imageUrl;
    }
}