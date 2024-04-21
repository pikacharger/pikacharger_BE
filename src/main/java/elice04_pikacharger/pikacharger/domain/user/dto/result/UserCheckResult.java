package elice04_pikacharger.pikacharger.domain.user.dto.result;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCheckResult {
    private String token;
    private Boolean isAdmin;
    private Boolean isSigned;
}

