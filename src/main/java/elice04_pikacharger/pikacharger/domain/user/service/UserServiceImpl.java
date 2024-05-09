package elice04_pikacharger.pikacharger.domain.user.service;


import com.amazonaws.services.s3.AmazonS3Client;
import elice04_pikacharger.pikacharger.domain.image.service.S3UploaderService;
import elice04_pikacharger.pikacharger.domain.user.dto.payload.*;
import elice04_pikacharger.pikacharger.domain.user.dto.result.UserResult;
import elice04_pikacharger.pikacharger.domain.user.entity.Role;
import elice04_pikacharger.pikacharger.domain.user.entity.User;
import elice04_pikacharger.pikacharger.domain.user.repository.UserRepository;
import elice04_pikacharger.pikacharger.exceptional.InvalidPasswordException;
import elice04_pikacharger.pikacharger.security.jwt.JwtUtil;
import elice04_pikacharger.pikacharger.security.jwt.MyTokenPayload;
import io.jsonwebtoken.Claims;
import jakarta.persistence.EntityExistsException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AmazonS3Client amazonS3Client;
    private final S3UploaderService s3UploaderService;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Transactional
    @Override
    public AuthResponseDto save(SignUpPayload payload){
        userRepository.findByEmail(payload.getEmail()).ifPresent(e->{
            throw new EntityExistsException();
        });
        checkDuplicateNickname(payload.getNickname());
        User saved = userRepository.save(
                User.builder()
                        .username(payload.getUsername())
                        .password(passwordEncoder.encode(payload.getPassword()))
                        .nickname(payload.getNickname())
                        .email(payload.getEmail())
                        .address(payload.getAddress())
                        .phoneNumber(payload.getPhoneNumber())
                        .chargerType(payload.getChargerType())
                        .roles(Collections.singleton(Role.USER))
                        .build()
        );

        return new AuthResponseDto(saved.getEmail(),null,null,saved.getRoles());
    }

    @Override
    public Boolean loginCheck(HttpServletRequest request){
        boolean result = jwtUtil.validateToken(jwtUtil.extractJwtFromRequest(request));
        return result;
    }

    @Override
    @Transactional
    public User updateUser(Long userId, MultipartFile profileImage, UserEditPayload payload) throws IOException {
        User user = userRepository.findById(userId).orElseThrow();
        String imgUrl = "";
        if(!profileImage.isEmpty() && profileImage != null){
            imgUrl = s3UploaderService.uploadSingleFile(profileImage,"images");
            ProfileImage image = ProfileImage.builder()
                    .imageUrl(imgUrl)
                    .user(user)
                    .build();

        }
        user.updateUser(payload);
        return user;
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
                jwtUtil.generateToken(new MyTokenPayload(user.getId(),user.getEmail(), user.getUsername(),user.getRoles())),
                jwtUtil.generateRefreshToken(),
                user.getRoles());
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
        userRepository.saveAndFlush(user);
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

    public User findUser(Long id){
        User findUser = userRepository.findById(id).orElseThrow();
        return findUser;
    }

    public String deleteUser(Long id){
        User user = userRepository.findById(id).orElseThrow();
        userRepository.deleteById(id);

        return user.getEmail();
    }

    public String retrieveUserEmail(String phoneNumber) {
        User foundUser = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow();

        return foundUser.getEmail();
    }

    public UserResponseDto findUserById(Long id){
        User findUser = userRepository.findById(id).orElseThrow();
        return new UserResponseDto(findUser);
    }

    @Transactional
    public String logoutUser(String refreshToken){
        if(jwtUtil.validateToken(refreshToken)){
            String userEmail = jwtUtil.extractEmail(refreshToken).orElseThrow();
            return jwtUtil.deleteRefreshByEmail(userEmail);
        }
        return null;
    }

    @Override
    public String createAccessByRefresh(String refreshToken) {

        Claims claims = jwtUtil.parseClaims(refreshToken);
        MyTokenPayload tokenPayload = MyTokenPayload.builder()
                .userId(claims.get("userId", Long.class))
                .name(claims.get("username", String.class))
                .email(claims.get("email", String.class))
                .build();

        return jwtUtil.generateToken(tokenPayload);
    }

    @Override
    public Long deleteByUserId(Long userId){
        userRepository.findById(userId).orElseThrow();
        userRepository.deleteById(userId);

        return userId;
    }

    @Override
    public Boolean checkDuplicateNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    @Override
    public  Optional<User> findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }











}
