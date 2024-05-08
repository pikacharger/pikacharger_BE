package elice04_pikacharger.pikacharger.domain.chat.controller;

import elice04_pikacharger.pikacharger.domain.chat.dto.ChatLogResponseDto;
import elice04_pikacharger.pikacharger.domain.chat.dto.ChatRoomRequestDto;
import elice04_pikacharger.pikacharger.domain.chat.dto.ChatRoomResponseDto;
import elice04_pikacharger.pikacharger.domain.chat.service.ChatLogService;
import elice04_pikacharger.pikacharger.domain.chat.service.ChatRoomService;
import elice04_pikacharger.pikacharger.exceptional.CustomException;
import elice04_pikacharger.pikacharger.exceptional.ErrorCode;
import elice04_pikacharger.pikacharger.security.jwt.JwtUtil;
import elice04_pikacharger.pikacharger.util.ApiResult;
import elice04_pikacharger.pikacharger.util.ApiUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    private final ChatLogService chatLogService;
    private final JwtUtil jwtUtil;

    //채팅방 목록 받기
    @Operation(summary = "채팅방 목록 조회", description = "사용자의 채팅방 목록을 조회한다", tags = { "Chat" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ChatRoomResponseDto.class))))
    })
    @GetMapping("/rooms")
    public ApiResult<List<ChatRoomResponseDto>> findAllChatRoom(@RequestHeader("Authorization") String token){

        // 토큰 유효기간 확인
        try {
            Date current = new Date(System.currentTimeMillis());
            if(current.after(jwtUtil.extractExpirationDateFromToken(token))) {
                throw new CustomException(ErrorCode.EXPIRED_ACCESS_TOKEN);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Long userId = jwtUtil.extractUserIdFromToken(token);
        List<ChatRoomResponseDto> chatRooms = chatRoomService.findAllChatRoom(userId);
        return ApiUtils.success(chatRooms);
    }

    // 채팅방에서의 모든 채팅 받기
    @Operation(summary = "채팅방 메시지 조회", description = "채팅방의 모든 메시지를 조회한다", tags = { "Chat" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ChatLogResponseDto.class))))
    })
    @GetMapping("/{chatRoomId}/logs")
    public ApiResult<List<ChatLogResponseDto>> getAllChatLog(@PathVariable("chatRoomId") Long chatRoomId, @RequestHeader("Authorization") String token) {
        Long userId = jwtUtil.extractUserIdFromToken(token);
        List<ChatLogResponseDto> chatLogs = chatLogService.getAllChatLog(chatRoomId);
        return ApiUtils.success(chatLogs);
    }

    // 채팅방 생성
    @Operation(summary = "채팅방 생성", description = "유저가 게시글에 메시지를 보낸다", tags = { "Chat" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = ChatRoomResponseDto.class)))
    })
    @PostMapping("")
    public ApiResult<ChatRoomResponseDto> createChatRoom(@RequestBody ChatRoomRequestDto chatRoomRequestDto, @RequestHeader("Authorization") String token) {
        // 토큰 유효기간 확인
        try {
            Date current = new Date(System.currentTimeMillis());
            if(current.after(jwtUtil.extractExpirationDateFromToken(token))){
                throw new CustomException(ErrorCode.EXPIRED_ACCESS_TOKEN);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Long chargerId = chatRoomRequestDto.getCharger().getId();
        Long senderId = jwtUtil.extractUserIdFromToken(token);
        ChatRoomResponseDto chatRoomResponseDto = chatRoomService.save(chargerId,senderId);

        return ApiUtils.success(chatRoomResponseDto);
    }
}