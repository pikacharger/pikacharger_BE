package elice04_pikacharger.pikacharger.domain.chat.controller;

import elice04_pikacharger.pikacharger.domain.chat.dto.ChatLogResponseDto;
import elice04_pikacharger.pikacharger.domain.chat.dto.ChatRoomResponseDto;
import elice04_pikacharger.pikacharger.domain.chat.dto.CreateChatRoomResponseDto;
import elice04_pikacharger.pikacharger.domain.chat.dto.SingleChatRoomResponseDto;
import elice04_pikacharger.pikacharger.domain.chat.service.ChatLogService;
import elice04_pikacharger.pikacharger.domain.chat.service.ChatRoomService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chatroom")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    private final ChatLogService chatLogService;
    private final JwtUtil jwtUtil;

    // 단일 채팅방 조회
    @Operation(summary = "단일 채팅방 조회", description = "요천한 하나의 채팅방을 조회한다", tags = { "Chat" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ChatLogResponseDto.class))))
    })
    @GetMapping("/{chatRoomId}")
    public ApiResult<SingleChatRoomResponseDto> getChatRoom(@PathVariable("chatRoomId") Long chatRoomId) {
        SingleChatRoomResponseDto chatRoom = chatRoomService.findById(chatRoomId);
        if (chatRoom == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 채팅방입니다.");
        }
        return ApiUtils.success(chatRoom);
    }

    // 채팅방 목록 받기
    @Operation(summary = "채팅방 목록 조회", description = "사용자의 채팅방 목록을 조회한다", tags = { "Chat" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ChatRoomResponseDto.class))))
    })
    @GetMapping("/rooms")
    public ApiResult<List<ChatRoomResponseDto>> findAllChatRoom(@AuthenticationPrincipal Long userId){
        List<ChatRoomResponseDto> chatRooms = chatRoomService.findAllChatRoom(userId);
        return ApiUtils.success(chatRooms);
    }

    // 채팅방에서의 모든 채팅 받기
    @Operation(summary = "채팅방 메시지 조회", description = "채팅방의 모든 메시지를 조회한다", tags = { "Chat" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ChatLogResponseDto.class))))
    })
    @GetMapping("/{chatRoomId}/logs")
    public ApiResult<List<ChatLogResponseDto>> getAllChatLog(@PathVariable("chatRoomId") Long chatRoomId, @AuthenticationPrincipal Long userId) {
        List<ChatLogResponseDto> chatLogs = chatLogService.getAllChatLog(chatRoomId, userId);
        return ApiUtils.success(chatLogs);
    }

    // 채팅방 생성
    @Operation(summary = "채팅방 생성", description = "유저가 게시글에 메시지를 보낸다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = ChatRoomResponseDto.class)))
    })
    @PostMapping("")
    public ResponseEntity<CreateChatRoomResponseDto> createChatRoom(@RequestParam Long chargerId, @AuthenticationPrincipal Long userId) {
        CreateChatRoomResponseDto chatroomId = chatRoomService.save(chargerId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(chatroomId);
    }
}