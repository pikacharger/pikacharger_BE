package elice04_pikacharger.pikacharger.domain.user.service;

import elice04_pikacharger.pikacharger.domain.user.dto.payload.*;
import elice04_pikacharger.pikacharger.domain.user.dto.result.UserResult;
import jakarta.servlet.http.HttpServletRequest;

public interface UserService {


    AuthResponseDto save(SignUpPayload payload);

    Long updateUser(String email,UserEditPayload payload);

    AuthResponseDto signIn(SignInPayload payload);

    Boolean loginCheck(HttpServletRequest request);

    Boolean checkDuplicate(DuplicateCheckDto dto);

    UserResult findOneById(Long id);

    void updatePassword(String email, String currentPassword, String newPassword);
}
