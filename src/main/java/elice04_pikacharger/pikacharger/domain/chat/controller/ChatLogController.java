package elice04_pikacharger.pikacharger.domain.chat.controller;

import elice04_pikacharger.pikacharger.domain.chat.dto.ChatLogRequestDto;
import elice04_pikacharger.pikacharger.domain.chat.service.ChatLogService;
import elice04_pikacharger.pikacharger.util.ApiResult;
import elice04_pikacharger.pikacharger.util.ApiUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/chatlog")
@RestController
@RequiredArgsConstructor
public class ChatLogController {
    private final ChatLogService chatLogService;

    @Operation(summary = "메시지 저장", description = "웹소켓 메시지 정보를 DB에 저장한다", tags = { "Chat" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @PostMapping("/{chatRoomId}/chatlogs")
    public ApiResult<Long> createChatLog(@AuthenticationPrincipal Long userId, @PathVariable("chatRoomId") Long chatRoomId, @RequestBody ChatLogRequestDto chatLogRequestDto) {
        Long createdChatLogId = chatLogService.save(userId, chatRoomId, chatLogRequestDto);
        return ApiUtils.success(createdChatLogId);
    }
}