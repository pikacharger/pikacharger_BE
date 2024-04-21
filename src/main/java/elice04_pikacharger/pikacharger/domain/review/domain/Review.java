package elice04_pikacharger.pikacharger.domain.review.domain;

import elice04_pikacharger.pikacharger.domain.common.BaseEntity;
import elice04_pikacharger.pikacharger.domain.image.domain.ReviewImage;
import elice04_pikacharger.pikacharger.domain.review.dto.payload.ReviewModifyPayload;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "review")
public class Review extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    private String content;
    private Integer rating;
    private String scope;

    @ManyToOne
    @JoinColumn(name = "user_id") //user가 삭제되면 review도 함께 삭제
    private User userId;

    @ManyToOne
    @JoinColumn(name = "used_charger_id") //charger가 삭제되면 review도 함께 삭제
    private Charger usedCharger;

    @OneToMany(mappedBy = "review", cascade = CascadeType.REMOVE) //review 삭제시 관련 image도 함께 삭제
    private List<ReviewImage> review_image = new ArrayList<>();

    @Builder
    public Review(String content, Integer rating, String scope){
        this.content = content;
        this.rating = rating;
        this.scope = scope;
    }

    public Long update(ReviewModifyPayload reviewDTO){
        this.content = reviewDTO.getContent();
        this.rating = reviewDTO.getRating();
        return this.id;
    }
}
