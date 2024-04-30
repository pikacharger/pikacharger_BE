package elice04_pikacharger.pikacharger.domain.user.service;


import elice04_pikacharger.pikacharger.domain.common.repository.RoleRepository;
import elice04_pikacharger.pikacharger.domain.user.dto.payload.DuplicateCheckDto;
import elice04_pikacharger.pikacharger.domain.user.dto.payload.SignInPayload;
import elice04_pikacharger.pikacharger.domain.user.dto.payload.SignUpPayload;
import elice04_pikacharger.pikacharger.domain.user.dto.payload.UserEditPayload;
import elice04_pikacharger.pikacharger.domain.user.dto.result.UserResult;
import elice04_pikacharger.pikacharger.domain.user.entity.User;
import elice04_pikacharger.pikacharger.domain.user.entity.UserRole;
import elice04_pikacharger.pikacharger.domain.user.repository.UserRepository;
import elice04_pikacharger.pikacharger.exceptional.InvalidPasswordException;
import elice04_pikacharger.pikacharger.security.JwtUtil;
import elice04_pikacharger.pikacharger.security.MyTokenPayload;
import jakarta.persistence.EntityExistsException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.lang.reflect.Member;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RoleRepository roleRepository;

    @Transactional
    @Override
    public Long save(SignUpPayload payload){
        if(userRepository.existsByEmail(payload.getEmail())){
            throw new EntityExistsException("이메일 중복입니다.");
        }

        User saved = userRepository.save(
                User.builder()
                        .username(payload.getUsername())
                        .password(passwordEncoder.encode(payload.getPassword()))
                        .nickname(payload.getNickname())
                        .email(payload.getEmail())
                        .address(payload.getAddress())
                        .phoneNumber(payload.getPhoneNumber())
                        .chargerType(payload.getChargerType())
                        .profileImage(payload.getProfileImage())
                        .build()
        );
        saved.addRole(UserRole.builder().
                user(saved).role(roleRepository
                        .findById(payload.getRoleId())
                        .orElseThrow()).build());
        return saved.getId();
    }

    @Override
    public Boolean loginCheck(HttpServletRequest request){
        boolean result = jwtUtil.validateToken(jwtUtil.extractJwtFromRequest(request));
        return result;
    }

    @Override
    public Long updateUser(String email,UserEditPayload payload){
        User user = userRepository.findByEmail(email);

        if(user == null){
            throw new IllegalArgumentException("???????? 되겠냐고");
        }
        user.editNickname(payload);
        return user.getId();
    }

    @Override
    public String signIn(SignInPayload payload){
        User user = userRepository.findByEmail(payload.getEmail());
        if(user == null || !passwordEncoder.matches(payload.getPassword(), user.getPassword())){
            throw new IllegalArgumentException("입력된 정보를 확인해 주세요.");
        }
        return jwtUtil.generateToken(new MyTokenPayload(user.getEmail(), user.getUsername(),user.getRoles().stream().map(ur->ur.getRole().getName()).toList()));

    }


    @Override
    public UserResult findOneById(Long id){
        return null;
    }

    @Override
    public Boolean checkDuplicate(DuplicateCheckDto dto){
        if (StringUtils.hasText(dto.getUsername())) {
            if (userRepository.existsByUsername(dto.getUsername()))
                throw new EntityExistsException("이미 존재하는 아이디입니다.");
        } else if (StringUtils.hasText(dto.getEmail())) {
            if (userRepository.existsByEmail(dto.getEmail()))
                throw new EntityExistsException("이미 존재하는 이메일입니다.");
        } else if (StringUtils.hasText(dto.getNickname())) {
            if (userRepository.existsByNickname(dto.getNickname()))
                throw new EntityExistsException("이미 존재하는 닉네임입니다.");
        }
        return true;
    }

    @Override
    @Transactional
    public void updatePassword(String email, String currentPassword, String newPassword) {
        User user = validatePassword(email, currentPassword);
        user.updatePassword(passwordEncoder.encode((newPassword)));
    }

    public User validatePassword(String email, String password){
        User user = userRepository.findByEmail(email);
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new InvalidPasswordException("INVALID PASSWORD");
        }
        return user;
    }



}
