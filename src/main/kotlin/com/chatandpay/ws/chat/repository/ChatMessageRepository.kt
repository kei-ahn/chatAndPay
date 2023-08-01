package com.chatandpay.ws.chat.repository

import com.chatandpay.ws.chat.entity.PrivateChatMessage
import org.bson.types.ObjectId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.mongodb.core.query.BasicQuery
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.TextCriteria
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import javax.persistence.EntityManager
import javax.persistence.TypedQuery


interface PrivateChatMessageRepository : MongoRepository<PrivateChatMessage, ObjectId> {
//    @Query("SELECT MAX(cm.sequenceNumber) FROM PrivateChatMessage cm WHERE cm.chatRoomId = ?1")
//    fun findLatestSequenceNumberByChatRoomId(chatRoomId: ObjectId?): ObjectId?

    fun findByMessageContaining(searchKeyword: String): List<PrivateChatMessage>


}

