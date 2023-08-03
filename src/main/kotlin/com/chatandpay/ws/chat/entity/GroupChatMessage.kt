package com.chatandpay.ws.chat.entity

import com.chatandpay.ws.chat.dto.GroupChatMessageDto
import com.chatandpay.ws.utils.toEpochMillis
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.TextIndexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "GroupChatMessage")
data class GroupChatMessage(
    @Id
    var id: String? = null,

    var chatRoomId: Long,

    var senderId: Long,

    var senderName: String,

//    @TextIndexed
    var message: String,

    var createdAt: Long = LocalDateTime.now().toEpochMillis()
) {
    companion object {
        @JvmStatic
        fun create(chatRoomId: Long, groupChatMessageDto: GroupChatMessageDto): GroupChatMessage {
            return GroupChatMessage(
                chatRoomId = chatRoomId,
                message = groupChatMessageDto.message,
                senderId = groupChatMessageDto.senderId,
                senderName = groupChatMessageDto.senderName
            )
        }
    }
}
