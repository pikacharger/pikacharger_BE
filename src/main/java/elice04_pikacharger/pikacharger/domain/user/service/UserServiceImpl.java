package elice04_pikacharger.pikacharger.domain.user.service;


import elice04_pikacharger.pikacharger.domain.user.dto.payload.*;
import elice04_pikacharger.pikacharger.domain.user.dto.result.UserResult;
import elice04_pikacharger.pikacharger.domain.user.entity.User;
import elice04_pikacharger.pikacharger.domain.user.repository.UserRepository;
import elice04_pikacharger.pikacharger.exceptional.InvalidPasswordException;
import elice04_pikacharger.pikacharger.security.jwt.JwtUtil;
import elice04_pikacharger.pikacharger.security.jwt.MyTokenPayload;
import jakarta.persistence.EntityExistsException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    @Override
    public AuthResponseDto save(SignUpPayload payload){
        userRepository.findByEmail(payload.getEmail()).ifPresent(e->{
            throw new EntityExistsException();
        });

        User saved = userRepository.save(
                User.builder()
                        .username(payload.getUsername())
                        .password(passwordEncoder.encode(payload.getPassword()))
                        .nickname(payload.getNickname())
                        .email(payload.getEmail())
                        .address(payload.getAddress())
                        .phoneNumber(payload.getPhoneNumber())
                        .role(payload.getRole())
                        .build()
        );

        return new AuthResponseDto(saved.getEmail(),null,saved.getRole());
    }

    @Override
    public Boolean loginCheck(HttpServletRequest request){
        boolean result = jwtUtil.validateToken(jwtUtil.extractJwtFromRequest(request));
        return result;
    }

    @Override
    public Long updateUser(String email,UserEditPayload payload){
        Optional<User> user = userRepository.findByEmail(email);

        if(user == null){
            throw new IllegalArgumentException("???????? 되겠냐고");
        }
        return user.get().getId();
    }

    @Override
    public AuthResponseDto signIn(SignInPayload payload){
        User user = userRepository.findByEmail(payload.getEmail())
                .orElseThrow(() -> new SecurityException());
        if(!passwordEncoder.matches(payload.getPassword(), user.getPassword())){
            throw new IllegalArgumentException("입력된 정보를 확인해 주세요.");
        }
        return new AuthResponseDto(
                user.getEmail(),
                jwtUtil.generateToken(new MyTokenPayload(user.getEmail(), user.getUsername(),user.getRole())),
                user.getRole());
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
        Optional<User> user = userRepository.findByEmail(email);
        if(!passwordEncoder.matches(password, user.get().getPassword())){
            throw new InvalidPasswordException("INVALID PASSWORD");
        }
        return user.get();
    }

    public User getUser(Long id){
        return userRepository.findById(id).get();
    }



}
