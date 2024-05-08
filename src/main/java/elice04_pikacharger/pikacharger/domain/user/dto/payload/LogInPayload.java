package elice04_pikacharger.pikacharger.domain.user.dto.payload;

import elice04_pikacharger.pikacharger.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LogInPayload {
    private String token;
    private User user;
}
