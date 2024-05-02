package elice04_pikacharger.pikacharger.domain.chat.controller;

import elice04_pikacharger.pikacharger.domain.chat.dto.ChatLogRequestDto;
import elice04_pikacharger.pikacharger.domain.chat.dto.ChatLogResponseDto;
import elice04_pikacharger.pikacharger.domain.chat.service.ChatLogService;
import elice04_pikacharger.pikacharger.util.ApiResult;
import elice04_pikacharger.pikacharger.util.ApiUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/chatlog")
@RestController
@RequiredArgsConstructor
public class ChatLogController {
    private final ChatLogService chatLogService;

    @Operation(summary = "메시지 전송", description = "메시지를 해당 채팅방에 전송한다", tags = { "Chat" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = ChatLogResponseDto.class)))
    })

    @PostMapping("/{chatRoomId}/chatlogs")
    public ApiResult<ChatLogResponseDto> createChatLog(@PathVariable("chatRoomId") Long chatRoomId, @RequestBody ChatLogRequestDto chatLogRequestDto) {
        ChatLogRequestDto chatLogRequestDto = chatLogService.save(chatRoomId, chatLogRequestDto);
        return ApiUtils.success(chatLogRequestDto);
    }
}
