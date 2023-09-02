package com.chatandpay.ws.chat.service

import com.chatandpay.ws.chat.dto.ChatRoomDto
import com.chatandpay.ws.chat.dto.GroupChatRoomDto
import org.springframework.stereotype.Service
import com.chatandpay.ws.chat.entity.ChatRoom
import com.chatandpay.ws.chat.entity.UserChatRoom
import com.chatandpay.ws.chat.repository.ChatRoomRepository
import com.chatandpay.ws.chat.repository.UserChatRoomRepository
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.ResponseStatus
import java.util.*

@Service
class ChatRoomService(
    private val chatRoomRepository: ChatRoomRepository,
    private val userChatRoomRepository: UserChatRoomRepository
) {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    class ChatRoomCreationException(message: String, cause: Throwable) : RuntimeException(message, cause)

    fun findAllRoom(pageable: Pageable): List<ChatRoom> {
        val chatRooms = chatRoomRepository.findAll(pageable).content
        return chatRooms
    }

    fun findById(id: String): ChatRoom? {
        val optionalChatRoom: Optional<ChatRoom> = chatRoomRepository.findById(id)
        println(optionalChatRoom)
        return optionalChatRoom.orElse(null)
    }

    fun createPrivateChatRoom(chatRoomDto: ChatRoomDto): ChatRoom {
        try {
            val chatRoom = ChatRoom.create(
                name = chatRoomDto.name,
                type = chatRoomDto.type
            )
            val saved = chatRoomRepository.save(chatRoom)
            
            return saved


        } catch (e: Exception) { e.printStackTrace()
            throw ChatRoomCreationException("Failed to create private chat room : ", e)
        }
    }

    // 그룹 유저 저장 - 채팅방 정보 / 채팅방 유저 정보 저장
    @Transactional()
    fun createGroupChatRoom(groupChatRoomDto: GroupChatRoomDto): ChatRoom{

        try {
            val groupChatRoom = ChatRoom.create(
                name = groupChatRoomDto.name,
                type = groupChatRoomDto.type
            )
            val chatRoom = chatRoomRepository.save(groupChatRoom);

            val groupUsers = groupChatRoomDto.chatUserIds.map{userId ->
                UserChatRoom.create(chatRoomId = chatRoom.id, chatUserId = userId);
            }

            userChatRoomRepository.saveAll(groupUsers);

            return chatRoom;

        } catch (e: Exception) { e.printStackTrace()
            throw ChatRoomService.ChatRoomCreationException("Failed to save group users", e)
        }
    }

}
