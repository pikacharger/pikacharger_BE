package elice04_pikacharger.pikacharger.domain.image.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import elice04_pikacharger.pikacharger.domain.charger.entity.Charger;
import elice04_pikacharger.pikacharger.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "charger_image")
public class ChargerImage extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "charger_id")
    @JsonBackReference
    private Charger charger; //이름 관련 찾아보기

    @Column(name = "img_url")
    private String imageUrl;

    @Builder
    public ChargerImage(Charger charger, String imageUrl){
        this.charger = charger;
        this.imageUrl = imageUrl;
    }
}
