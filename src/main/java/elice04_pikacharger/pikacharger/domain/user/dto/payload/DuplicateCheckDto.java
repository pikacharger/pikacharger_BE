package elice04_pikacharger.pikacharger.domain.user.dto.payload;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class DuplicateCheckDto {
    private String username;
    private String email;
    private String nickname;
}
