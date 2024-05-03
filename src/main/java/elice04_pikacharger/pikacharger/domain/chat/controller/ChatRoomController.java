package elice04_pikacharger.pikacharger.domain.chat.controller;

import elice04_pikacharger.pikacharger.domain.chat.dto.ChatLogResponseDto;
import elice04_pikacharger.pikacharger.domain.chat.dto.ChatRoomRequestDto;
import elice04_pikacharger.pikacharger.domain.chat.dto.ChatRoomResponseDto;
import elice04_pikacharger.pikacharger.domain.chat.service.ChatRoomService;
import elice04_pikacharger.pikacharger.exceptional.CustomException;
import elice04_pikacharger.pikacharger.exceptional.ErrorCode;
import elice04_pikacharger.pikacharger.security.JwtProvider;
import elice04_pikacharger.pikacharger.util.ApiResult;
import elice04_pikacharger.pikacharger.util.ApiUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;

public class ChatRoomController {
    private final ChatRoomResponseDto chatRoomResponseDto;
    private final ChatRoomService chatRoomService;
    private final JwtProvider jwtProvider;
    //TODO: JWT쪽 사용하지말고 클라이언트 토큰 받아오는건 어떤지?

    //채팅방 목록 받기
    @Operation(summary = "채팅방 목록 조회", description = "사용자의 채팅방 목록을 조회한다", tags = { "Chat" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ChatRoomResponseDto.class))))
    })
    @GetMapping("/rooms")
    public ApiResult<List<ChatRoomResponseDto>> findAllChatRoom() throws Exception {

        //토큰 유효기간 확인
        try {
            Date current = new Date(System.currentTimeMillis());
            if(current.after(jwtProvider.getExp())){
                throw new CustomException(ErrorCode.EXPIRED_ACCESS_TOKEN);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Long userId = jwtProvider.getUserId();
        List<ChatRoomResponseDto> chatRooms = chatRoomService.findAllChatRoom(userId);
        return ApiUtils.success(chatRooms);
    }

    // 채팅방에서의 모든 채팅 받기
    @Operation(summary = "채팅방 메시지 조회", description = "채팅방의 모든 메시지를 조회한다", tags = { "Chat" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ChatLogResponseDto.class))))
    })
    @GetMapping("/{chatRoomId}/messages")
    public ApiResult<List<ChatLogResponseDto>> getAllChatMessage(@PathVariable("chatRoomId") Long chatRoomId) throws Exception {
        Long userId = jwtProvider.getUserId();
        List<ChatLogResponseDto> chatMessages = chatRoomService.getAllChatMessage(chatRoomId);
        return ApiUtils.success(chatMessages);
    }

    // 채팅방 생성
    @Operation(summary = "채팅방 생성", description = "유저가 게시글에 메시지를 보낸다", tags = { "Chat" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = ChatRoomResponseDto.class)))
    })
    @PostMapping("")
    public ApiResult<ChatRoomResponseDto> createChatRoom(@RequestBody ChatRoomRequestDto chatRoomRequestDto) throws Exception {
        //토큰 유효기간 확인
        try {
            Date current = new Date(System.currentTimeMillis());
            if(current.after(jwtProvider.getExp())){
                throw new CustomException(ErrorCode.EXPIRED_ACCESS_TOKEN);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Long postId = chatRoomRequestDto.getRoomId();
        Long volunteerId = jwtProvider.getUserId();
        ChatRoomResponseDto chatRoomResponseDto = chatRoomService.save(roomId);
        // 채팅방 생성 시 자동으로 웹소켓 연결
        // 서비스에 createChatRoom 추가해야함

        return ApiUtils.success(chatRoomResponseDto);
    }
}
