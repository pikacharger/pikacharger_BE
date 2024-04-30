package elice04_pikacharger.pikacharger.domain.user.controller;


import elice04_pikacharger.pikacharger.domain.user.dto.payload.SignInPayload;
import elice04_pikacharger.pikacharger.domain.user.dto.payload.SignUpPayload;
import elice04_pikacharger.pikacharger.domain.user.dto.result.UserCheckResult;
import elice04_pikacharger.pikacharger.domain.user.service.UserService;
import elice04_pikacharger.pikacharger.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "(계정)", description = "계정 관련")
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "회원가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "에러", content = @Content(schema = @Schema(implementation = Long.class)))})
    public ResponseEntity<Long> signUp(@RequestBody SignUpPayload payload) {
        Long saved = userService.save(payload);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    @Operation(summary = "로그인", description = "로그인")
    public ResponseEntity<String> signIn(@RequestBody SignInPayload payload) {
        String token = userService.signIn(payload);

        return new ResponseEntity<>(token, HttpStatus.OK);
    }

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
            result.setIsAdmin(jwtUtil.verify(token).getRoles().contains("admin"));
            result.setToken(token);
            result.setIsSigned(jwtUtil.validateToken(token));
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
