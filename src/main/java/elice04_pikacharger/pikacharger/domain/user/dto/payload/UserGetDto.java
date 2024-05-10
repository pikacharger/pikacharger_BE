package elice04_pikacharger.pikacharger.domain.user.dto.payload;

import elice04_pikacharger.pikacharger.domain.favorite.entity.Favorite;
import elice04_pikacharger.pikacharger.domain.user.entity.Role;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
public class UserGetDto {

    private Long id;
    private String name;
    private String email;
    private String nickname;
    private String address;
    private String phoneNumber;
    private Set<Role> roles = new HashSet<>();
    private String chargerType;
    private String profileImage;
    private List<Favorite> favorites = new ArrayList<>();
}
