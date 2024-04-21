package elice04_pikacharger.pikacharger.domain.user.service;

import elice04_pikacharger.pikacharger.domain.user.dto.payload.DuplicateCheckDto;
import elice04_pikacharger.pikacharger.domain.user.dto.payload.SignInPayload;
import elice04_pikacharger.pikacharger.domain.user.dto.payload.SignUpPayload;
import elice04_pikacharger.pikacharger.domain.user.dto.payload.UserEditPayload;
import elice04_pikacharger.pikacharger.domain.user.dto.result.UserResult;

public interface UserService {

    Long save(SignUpPayload payload);

    Long updateUser(String email,UserEditPayload payload);

    String signIn(SignInPayload payload);


    Boolean checkDuplicate(DuplicateCheckDto dto);

    UserResult findOneById(Long id);

    void updatePassword(String email, String currentPassword, String newPassword);
}
