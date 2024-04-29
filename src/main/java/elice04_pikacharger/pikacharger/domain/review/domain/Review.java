package elice04_pikacharger.pikacharger.domain.review.domain;

import elice04_pikacharger.pikacharger.domain.charger.entity.Charger;
import elice04_pikacharger.pikacharger.domain.common.BaseEntity;
import elice04_pikacharger.pikacharger.domain.image.domain.ReviewImage;
import elice04_pikacharger.pikacharger.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "review")
public class Review extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    private String content;
    private Integer rating;

    @ManyToOne
    @JoinColumn(name = "user_id") //user가 삭제되면 review도 함께 삭제
    private User user;

    @ManyToOne
    @JoinColumn(name = "charger_id") //charger가 삭제되면 review도 함께 삭제
    private Charger charger;

//    @OneToMany(mappedBy = "review", cascade = CascadeType.REMOVE) //review 삭제시 관련 image도 함께 삭제
//    private List<ReviewImage> imageUrl = new ArrayList<>();

    @Transient
    private List<ReviewImage> imgList = new ArrayList<>();

    @Builder
    public Review(String content, Integer rating, User user, Charger charger, List<ReviewImage> imgList){
        this.content = content;
        this.rating = rating;
        this.user = user;
        this.charger = charger;
        this.imgList = imgList;
    }

    public Long update(String content, Integer rating){
        this.content = content;
        this.rating = rating;
        return this.id;
    }
}
