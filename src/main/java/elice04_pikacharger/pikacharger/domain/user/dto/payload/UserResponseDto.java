package elice04_pikacharger.pikacharger.domain.user.dto.payload;

import elice04_pikacharger.pikacharger.domain.user.dto.result.UserResult;
import elice04_pikacharger.pikacharger.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserResponseDto {
    private Long id;
    private String userName;
    private String email;

    @Builder
    public UserResponseDto(UserResult userResult) {
        this.id = userResult.getId();
        this.userName = userResult.getName();
        this.email = userResult.getEmail();
    }
}


