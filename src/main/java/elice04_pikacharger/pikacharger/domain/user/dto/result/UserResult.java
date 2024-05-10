package elice04_pikacharger.pikacharger.domain.user.dto.result;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
@Getter
public class UserResult {
    private Long id;
    private String username;
    private String nickName;
    private String email;
}
