package elice04_pikacharger.pikacharger.domain.user.dto.result;


import lombok.Data;

@Data
public class UserResult {
    private Long id;
    private String name;
    private String nickName;
    private String email;
}
