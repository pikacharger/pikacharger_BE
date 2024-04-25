package elice04_pikacharger.pikacharger.domain.charger.entity;

import elice04_pikacharger.pikacharger.domain.chargertype.entity.ChargerType;
import elice04_pikacharger.pikacharger.domain.common.BaseEntity;
import elice04_pikacharger.pikacharger.domain.favorite.entity.Favorite;
import elice04_pikacharger.pikacharger.domain.image.domain.ChargerImage;
import elice04_pikacharger.pikacharger.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Charger extends BaseEntity {

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
    private double memberPrice;
    private double nonmemberPrice;
    private double personalPrice;
    private double avgRate;

    @Enumerated(EnumType.STRING)
    private ChargerRole chargerRole;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "charger", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<ChargerType> chargerTypes = new ArrayList<>();

    @OneToMany(mappedBy = "charger", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<ChargerImage> chargerImages = new ArrayList<>();

    @OneToMany(mappedBy = "charger", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Favorite> favorites = new ArrayList<>();

    @Builder(builderMethodName = "publicChargerBuilder", builderClassName = "publicChargerBuilder")
    public static Charger publicCharger(String chargerLocation, String chargerName, String chargingSpeed, String companyName, String chargerStatus, double latitude, double longitude){
        return new Charger(chargerLocation, chargerName, chargingSpeed, companyName, chargerStatus, latitude, longitude);
    }

    @Builder(builderMethodName = "personalChargerBuilder", builderClassName = "personalChargerBuilder")
    public static Charger personalCharger(String chargerLocation, String chargerName, String chargingSpeed, double latitude, double longitude, String content, double personalPrice, User user) {
        return new Charger(chargerLocation, chargerName, chargingSpeed, latitude, longitude, content, personalPrice, user);
    }

    public void updateCharger(String chargerLocation, String chargerName, String chargingSpeed, double latitude, double longitude, String content, double personalPrice){
        this.chargerLocation = chargerLocation;
        this.chargerName = chargerName;
        this.chargingSpeed = chargingSpeed;
        this.latitude = latitude;
        this.longitude = longitude;
        this.content = content;
        this.personalPrice = personalPrice;
    }

    private Charger(String chargerLocation, String chargerName, String chargingSpeed, String companyName, String chargerStatus, double latitude, double longitude){
        this.chargerLocation = chargerLocation;
        this.chargerName = chargerName;
        this.chargingSpeed = chargingSpeed;
        this.companyName = companyName;
        this.chargerStatus = chargerStatus;
        this.latitude = latitude;
        this.longitude = longitude;
        this.chargerRole = ChargerRole.PUBLICCHARGE;
    }

    private Charger(String chargerLocation, String chargerName, String chargingSpeed, double latitude, double longitude, String content, double personalPrice, User user) {
        this.chargerLocation = chargerLocation;
        this.chargerName = chargerName;
        this.chargingSpeed = chargingSpeed;
        this.latitude = latitude;
        this.longitude = longitude;
        this.content = content;
        this.personalPrice = personalPrice;
        this.chargerRole = ChargerRole.PERSONALCHARGER;
        this.user = user;
    }

}
