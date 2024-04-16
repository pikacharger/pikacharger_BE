package elice04_pikacharger.pikacharger.domain.review.domain;

import elice04_pikacharger.pikacharger.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

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
    private String reviewImage;
    private Integer rating;
    private String scope;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "used_charger_id")
    private Charger usedCharger;


    @Builder
    public Review(String content, String reviewImage, Integer rating, String scope){
        this.content = content;
        this.reviewImage = reviewImage;
        this.rating = rating;
        this.scope = scope;
    }
}
