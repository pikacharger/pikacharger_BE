package elice04_pikacharger.pikacharger.domain.user.service;

import elice04_pikacharger.pikacharger.domain.user.dto.payload.*;
import elice04_pikacharger.pikacharger.domain.user.dto.result.UserResult;
import elice04_pikacharger.pikacharger.domain.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {


    AuthResponseDto save(SignUpPayload payload);

    User updateUser(Long userId, MultipartFile profileImage, UserEditPayload payload);

    AuthResponseDto signIn(SignInPayload payload);

    Boolean loginCheck(HttpServletRequest request);

    Boolean checkDuplicate(DuplicateCheckDto dto);

    UserResult findOneById(Long id);

    void updatePassword(String email, String currentPassword, String newPassword);

    User findUser(Long id);

    String deleteUser(Long id);

    String retrieveUserEmail(String phoneNumber);

    UserResponseDto findUserById(Long id);
    public String logoutUser(String refreshToken);


    String createAccessByRefresh(String refreshToken);

    Long deleteByUserId(Long userId);

    Boolean checkDuplicateNickname(String nickname);
}
