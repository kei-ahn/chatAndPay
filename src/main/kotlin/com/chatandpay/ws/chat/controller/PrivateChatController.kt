package com.chatandpay.ws.chat.controller

import com.chatandpay.ws.chat.dto.ChatMessageDto
import com.chatandpay.ws.chat.dto.SearchKeywordDto
import com.chatandpay.ws.chat.entity.PrivateChatMessage
import com.chatandpay.ws.chat.service.ChatMessageService

import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestParam


@Controller
class PrivateChatController(
    private val chatMessageService: ChatMessageService
) {

    @MessageMapping("/pub/chat/room/{roomId}/enter")
    @SendTo("/sub/chat/room/{roomId}")
    fun enter(@DestinationVariable roomId: String,  chatMessageDto: ChatMessageDto): PrivateChatMessage {

        println("접속");
        println(chatMessageDto);
//        chatMessageService.savePrivateChatMessage(chatMessageDto);
        return chatMessageService.getChatMessagesByRoomId(chatMessageDto);

    }


    @MessageMapping("/pub/chat/room/{roomId}")
    @SendTo("/sub/chat/room/{roomId}")
    fun comment(@DestinationVariable roomId: String,  chatMessageDto: ChatMessageDto): PrivateChatMessage {

        chatMessageService.savePrivateChatMessage(chatMessageDto);
        return chatMessageService.getChatMessage(chatMessageDto);
    }

    @MessageMapping("/pub/chat/room/{roomId}/message")
    @SendTo("/sub/chat/room/{roomId}")
    fun searchMesage(@DestinationVariable roomId: String, payload:SearchKeywordDto): List<PrivateChatMessage>? {
        println(payload.searchKeyword);
        return chatMessageService.searchMessages(payload.searchKeyword)

    }

}