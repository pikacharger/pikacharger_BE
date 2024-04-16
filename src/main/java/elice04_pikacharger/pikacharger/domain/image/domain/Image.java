package elice04_pikacharger.pikacharger.domain.image.domain;

import elice04_pikacharger.pikacharger.domain.common.BaseEntity;
import elice04_pikacharger.pikacharger.domain.review.domain.Review;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "image")
public class Image extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "charger_id")
    private Charger chargerId;

    @ManyToOne
    @JoinColumn(name = "review_image")
    private Review reviewImage;

    @Column(name = "img_url")
    private String imageUrl;
}
