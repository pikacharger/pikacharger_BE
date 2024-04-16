package elice04_pikacharger.pikacharger.domain.image.domain;

import elice04_pikacharger.pikacharger.domain.common.BaseEntity;
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

    @Column(name = "img_url")
    private String imageUrl;
}
