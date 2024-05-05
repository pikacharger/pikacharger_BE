package elice04_pikacharger.pikacharger.domain.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER("ROLE_USER","유저")
    ,ADMIN("ROLE_ADMIN","관리자")
    ,GUEST("GUEST","게스트권한");

    private final String key;
    private final String title;

}
