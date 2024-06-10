package elice04_pikacharger.pikacharger.domain.user.dto.payload;

import elice04_pikacharger.pikacharger.domain.favorite.entity.Favorite;
import elice04_pikacharger.pikacharger.domain.user.entity.Role;
import elice04_pikacharger.pikacharger.domain.user.entity.User;
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
    private String username;
    private String email;
    private String nickName;
    private String address;
    private String phoneNumber;
    private Set<Role> roles = new HashSet<>();
    private String chargerType;
    private String profileImage;
    private List<Favorite> favorites = new ArrayList<>();

    public UserGetDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.nickName = user.getNickName();
        this.profileImage = user.getProfileImage();
    }
}
