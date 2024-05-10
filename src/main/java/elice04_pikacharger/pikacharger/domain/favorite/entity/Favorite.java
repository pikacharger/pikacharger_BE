package elice04_pikacharger.pikacharger.domain.favorite.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import elice04_pikacharger.pikacharger.domain.charger.entity.Charger;
import elice04_pikacharger.pikacharger.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Favorite{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "charger_id")
    private Charger charger;

    @Builder
    public Favorite(User user, Charger charger) {
        this.user = user;
        this.charger = charger;
    }
}
