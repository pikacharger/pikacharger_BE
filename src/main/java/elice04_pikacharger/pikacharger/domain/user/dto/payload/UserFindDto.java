package elice04_pikacharger.pikacharger.domain.user.dto.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserFindDto {

    @NotBlank(message = "전화번호를 입력해주세요.")
    private String phoneNumber;
}

