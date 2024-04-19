package elice04_pikacharger.pikacharger.domain.chargertype.entity;

import elice04_pikacharger.pikacharger.domain.charger.entity.Charger;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class ChargerType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;

    @ManyToOne
    @JoinColumn(name = "charger_id")
    private Charger charger;

    @Builder
    public ChargerType (String type, Charger charger){
        this.type = type;
        this.charger = charger;
    }
}
