package elice04_pikacharger.pikacharger.domain.user.controller;


import elice04_pikacharger.pikacharger.domain.user.dto.payload.*;
import elice04_pikacharger.pikacharger.domain.user.dto.result.UserCheckResult;
import elice04_pikacharger.pikacharger.domain.user.dto.result.UserFindResponse;
import elice04_pikacharger.pikacharger.domain.user.entity.CustomUserDetails;
import elice04_pikacharger.pikacharger.domain.user.entity.User;
import elice04_pikacharger.pikacharger.domain.user.repository.UserRepository;
import elice04_pikacharger.pikacharger.domain.user.service.UserService;
import elice04_pikacharger.pikacharger.security.jwt.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "(계정)", description = "계정 관련")
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "회원가입 O")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "에러", content = @Content(schema = @Schema(implementation = Long.class)))})
    public ResponseEntity<AuthResponseDto> signUp(@RequestBody SignUpPayload payload) {
        AuthResponseDto saved = userService.save(payload);
        return  ResponseEntity.status(HttpStatus.CREATED)
                .body(saved);
    }

    @PostMapping("/signin")
    @Operation(summary = "로그인", description = "로그인 O")
    public ResponseEntity<LogInPayload> signIn(@RequestBody SignInPayload payload, HttpServletResponse response) {
        AuthResponseDto authResponseDto = userService.signIn(payload);

        User user = userRepository.findByEmail(authResponseDto.getEmail()).orElseThrow();
        LogInPayload logInPayload = new LogInPayload(authResponseDto.getJwtToken(), authResponseDto.getRefreshToken(), user);
        response.setHeader("Authorization",logInPayload.getToken());
        return  ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(logInPayload);
    }

    @PostMapping("/createAccessByRefresh")
    @Operation(summary = "리프레쉬 토큰으로 엑세스 토큰 발급", description = "리프레쉬 토큰으로 엑세스 토큰 발급")
    public ResponseEntity<String> createAccessByRefresh(@CookieValue(value = "refreshToken", required = false) Cookie cookie) {

        String refreshToken = "";
        if (cookie != null) {
            refreshToken= cookie.getValue();
        }

        return new ResponseEntity<>(userService.createAccessByRefresh(refreshToken), HttpStatus.OK);
    }


    @PostMapping("/logout")
    @Operation(summary = "로그아웃", description = "로그아웃")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization");
        System.out.printf(accessToken);
        if(accessToken != null && accessToken.startsWith("Bearer ")) {
            accessToken = accessToken.substring(7);
        }

        String deleteMessage = userService.logoutUser(jwtUtil.extractEmail(accessToken).orElseThrow());
        return ResponseEntity.status(HttpStatus.OK).body(deleteMessage);
    }


    @PostMapping("/accountId")
    @Operation(summary = "사용자 아이디 찾기", description = "회원가입 시 입력한 휴대폰 번호로 아이디(이메일) 찾기 O")
    public ResponseEntity<UserFindResponse> retrieveUserId(@RequestBody @Valid UserFindDto userFindDto){
        String userEmail = userService.retrieveUserEmail(userFindDto.getPhoneNumber());
        UserFindResponse userFindResponse = new UserFindResponse(userEmail);
        return ResponseEntity.status(HttpStatus.OK).body(userFindResponse);
    }

//    @GetMapping("/details")
//    @Operation(summary = "사용자 정보 조회", description = "토큰에서 추출한 userID를 통해 사용자에 대한 정보 출력")
//    public ResponseEntity<UserResponseDto> findCurrnetById(@AuthenticationPrincipal CustomUserDetails userDetails){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if(authentication == null || !authentication.isAuthenticated()){
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//
//        Long userId = userDetails.getUser().getId();
//        User user = userService.findUser(userId);
//
//        if (user == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        UserResponseDto userResponseDto = UserResponseDto.builder()
//                .user(user)
//                .build();
//        return ResponseEntity.ok(userResponseDto);
//    }

    @GetMapping("/logincheck")
    @Operation(summary = "로그인상태", description = "로그인 상태 확인")
    public ResponseEntity<Boolean> loginCheck(HttpServletRequest request) {
        boolean result = userService.loginCheck(request);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/check")
    @Operation(summary = "유저체크", description = "유저체크")
    public ResponseEntity<UserCheckResult> check(HttpServletRequest request) {
        String token = jwtUtil.extractJwtFromRequest(request);
        UserCheckResult result = new UserCheckResult("",false, false);
        if(token != null) {
            result.setToken(token);
            result.setIsSigned(jwtUtil.validateToken(token));
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PatchMapping("/updateUser")
    @Operation(summary = "사용자 정보 업데이트")
    public ResponseEntity<UserResponseDto> updateUser(@RequestPart(value = "file", required = false) MultipartFile multipartFile, @RequestPart(value = "userUpdateDto") UserEditPayload updateDto, @AuthenticationPrincipal CustomUserDetails tokenUser) throws IOException{
        User user = userService.updateUser(tokenUser.getMyTokenPayload().getUserId(),multipartFile ,updateDto);
        return new ResponseEntity<>(new UserResponseDto(user), HttpStatus.OK);
    }

    private void setRefreshCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie("refreshToken", token);
        // 1day
        cookie.setMaxAge(24 * 60 * 60);

        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    @DeleteMapping
    @Operation(summary = "유저 삭제", description = "유저 삭제")
    public ResponseEntity<Long> deleteUser(@AuthenticationPrincipal Long userId) {
        return new ResponseEntity<>(userService.deleteByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/check/nickname/{nickname}")
    @Operation(summary = "닉네임 중복체크")
    public ResponseEntity<Boolean> checkDuplicateNickname(@PathVariable("nickname") String nickname) {
        return new ResponseEntity<>(userService.checkDuplicateNickname(nickname), HttpStatus.OK);
    }

    @GetMapping("/info")
    @Operation(summary = "토큰으로 유저 정보 받아오기")
    public ResponseEntity<User> getUserByToken(HttpServletRequest request) {
        String token = jwtUtil.extractJwtFromRequest(request);
        String email = jwtUtil.extractEmail(token).orElseThrow();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }





}
