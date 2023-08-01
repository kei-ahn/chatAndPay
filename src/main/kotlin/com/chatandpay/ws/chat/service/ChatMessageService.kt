package com.chatandpay.ws.chat.service


import com.chatandpay.ws.chat.dto.ChatMessageDto
import com.chatandpay.ws.chat.dto.GroupChatMessageDto
import com.chatandpay.ws.chat.entity.GroupChatMessage
import com.chatandpay.ws.chat.entity.PrivateChatMessage
import com.chatandpay.ws.chat.repository.GroupChatMessageRepository
import com.chatandpay.ws.chat.repository.PrivateChatMessageRepository
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.index.TextIndexDefinition
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.TextCriteria
import org.springframework.stereotype.Service

@Service
class ChatMessageService (
    private val privateChatMessageRepository: PrivateChatMessageRepository,
    private val groupChatMessageRepository:GroupChatMessageRepository,
    ){

    fun savePrivateChatMessage(chatMessageDto: ChatMessageDto) {
        val chatMessage = PrivateChatMessage.create(chatMessageDto)
        privateChatMessageRepository.save(chatMessage)
    }

    fun saveEnterMessage() {

    }


    fun searchMessages(searchKeyword: String): List<PrivateChatMessage> {
        return privateChatMessageRepository.findByMessageContaining(searchKeyword)
    }

    fun getChatMessagesByRoomId(chatMessageDto: ChatMessageDto):PrivateChatMessage {

//        // 채팅방의 모든 채팅 기록 조회 -********** -> 시퀀스 번호 별로 마지막 10개 뽑아서 보여줘야함
//        val chatMessages = privateChatMessageRepository.findBySenderId(chatMessageDto.senderId)
//        // 나중에 현재 유저의 아이디 값으로 변경할 것
//
//        println(chatMessages);
//        if (chatMessages.isNotEmpty()) {
//            return chatMessages.map {chatMessage -> PrivateChatMessage.create(chatMessageDto)
//            }
//        }

//        // 접속했을때 시퀀스번호를 조회하고 할당함
//        val sequenceNumber = privateChatMessageRepository.findLatestSequenceNumberByChatRoomId(chatMessageDto.chatRoomId);
//        chatMessageDto.sequenceNumber = sequenceNumber?.plus(1)
        // 채팅방 기록이 없다면 최초 입장으로 파악 -> 입장했습니다 메시지 반환

        val chatMessage = PrivateChatMessage.create(chatMessageDto)
        privateChatMessageRepository.save(chatMessage)


        return PrivateChatMessage.createEnterMessage(chatMessageDto)

    }


    fun getChatMessage(chatMessageDto: ChatMessageDto):PrivateChatMessage  {
        return PrivateChatMessage.create(chatMessageDto)
    }


    fun saveGroupMessage(chatRoomId:Long,groupChatMessageDto: GroupChatMessageDto):List<GroupChatMessage> {
        try {
            val groupChatMessage = GroupChatMessage.create(chatRoomId,groupChatMessageDto);
            return listOf(groupChatMessageRepository.save(groupChatMessage));
        } catch (e: Exception) { e.printStackTrace()
            throw ChatRoomService.ChatRoomCreationException("Failed to create chat room", e)
        }
    }

}