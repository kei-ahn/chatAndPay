package com.chatandpay.ws.chat.dto

import com.chatandpay.ws.chat.entity.ChatRoom
import org.bson.types.ObjectId
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull


@Validated
data class CreateGroupChatRoomDto(
    @field:NotBlank(message = "Name cannot be blank")
    val name: String,

    @field:NotNull(message = "Type cannot be null")
    val type: ChatRoom.Type,

    @field:NotNull(message = "Type cannot be null")
    val chatUserIds: List<ObjectId>
)
//
data class UserChatRoomDto(
    val chatUserId: ObjectId,
    val chatRoomId: ObjectId,
)

