package elice04_pikacharger.pikacharger.domain.email.controller;

import elice04_pikacharger.pikacharger.domain.email.dto.EmailCheckDto;
import elice04_pikacharger.pikacharger.domain.email.dto.EmailRequestDto;
import elice04_pikacharger.pikacharger.domain.email.service.MailSendService;
import elice04_pikacharger.pikacharger.domain.user.entity.User;
import elice04_pikacharger.pikacharger.domain.user.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "(메일)", description = "메일 관련")
public class MailController {
    private final MailSendService mailSendService;
    private final UserRepository userRepository;

    @PostMapping("/mailSend")
    @Operation(summary = "인증 메일 보내기", description = "인증 메일 보내기")
    public ResponseEntity<String> mailSend(@RequestBody @Valid EmailRequestDto dto){
        Boolean checked = userRepository.existsByEmail(dto.getEmail());
        if(checked){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("이 이메일 주소는 이미 사용 중입니다. 다른 이메일 주소를 입력해 주세요.");
        }
        mailSendService.joinEmail(dto.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/mailauthCheck")
    @Operation(summary = "인증번호 체크", description = "인증 번호 체크")
    public String AuthCheck(@RequestBody @Valid EmailCheckDto dto){
        Boolean checked = mailSendService.CheckAuthNum(dto.getEmail(),dto.getAuthNum());
        if(checked){
            return "ok";
        }
        else{
            throw new NullPointerException("unAuthrized");
        }
    }
}
