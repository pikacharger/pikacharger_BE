package elice04_pikacharger.pikacharger.domain.review.domain;

import elice04_pikacharger.pikacharger.domain.common.BaseEntity;
import elice04_pikacharger.pikacharger.domain.image.domain.Image;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

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
    private User user;

    @ManyToOne
    @JoinColumn(name = "used_charger_id") //charger가 삭제되면 review도 함께 삭제
    private Charger usedCharger;

    @OneToMany(mappedBy = "review", cascade = CascadeType.REMOVE) //review 삭제시 관련 image도 함께 삭제
    private List<Image> review_image;

    @Builder
    public Review(String content, Integer rating, String scope){
        this.content = content;
        this.rating = rating;
        this.scope = scope;
    }
}
