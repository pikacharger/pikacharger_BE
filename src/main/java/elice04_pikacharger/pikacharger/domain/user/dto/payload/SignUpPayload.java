package elice04_pikacharger.pikacharger.domain.user.dto.payload;


import elice04_pikacharger.pikacharger.domain.user.entity.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUpPayload {
    @NotBlank(message = "이름은 필수 입력사항입니다.")
    private String username;

    @NotBlank(message = "이메일은 필수 입력사항입니다.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;

    @Pattern(regexp="^.{1,10}$", message="닉네임은 10자 이하로 설정해주세요.")
    private String nickname;

    @NotBlank(message = "비밀번호는 필수 입력사항입니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")
    private String password;
    private String address;
    private String phoneNumber;
    private String chargerType;

}
