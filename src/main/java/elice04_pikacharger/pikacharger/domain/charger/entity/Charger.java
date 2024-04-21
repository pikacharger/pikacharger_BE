package elice04_pikacharger.pikacharger.domain.charger.entity;

import elice04_pikacharger.pikacharger.domain.chargertype.entity.ChargerType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Charger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "charger_id")
    private Long id;
    private String chargerLocation;
    private String chargerName;
    private String chargingSpeed;
    private String chargerStatus;
    private String companyName;
    private double latitude;
    private double longitude;

    private String content;
    private int memberPrice;
    private int nonmemberPrice;
    private int personalPrice;
    private double avgRate;

    @Enumerated(EnumType.STRING)
    private ChargerRole chargerRole;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;

    @OneToMany(mappedBy = "charger", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<ChargerType> chargerTypes = new ArrayList<>();

    @Builder
    public Charger(String chargerLocation, String chargerName, String chargingSpeed, String companyName, String chargerStatus, double latitude, double longitude){
        this.chargerLocation = chargerLocation;
        this.chargerName = chargerName;
        this.chargingSpeed = chargingSpeed;
        this.companyName = companyName;
        this.chargerStatus = chargerStatus;
        this.latitude = latitude;
        this.longitude = longitude;
        this.chargerRole = ChargerRole.PUBLICCHARGE;
    }

}
