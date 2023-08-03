package com.chatandpay.ws.chat.repository

import com.chatandpay.ws.chat.entity.PrivateChatMessage
import org.bson.types.ObjectId
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.BasicQuery
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.TextCriteria
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import java.util.regex.Pattern
import javax.persistence.EntityManager
import javax.persistence.TypedQuery


interface PrivateChatMessageRepository : MongoRepository<PrivateChatMessage, ObjectId> {
//    @Query("SELECT MAX(cm.sequenceNumber) FROM PrivateChatMessage cm WHERE cm.chatRoomId = ?1")
//    fun findLatestSequenceNumberByChatRoomId(chatRoomId: ObjectId?): ObjectId?




}



@Repository
class PrivateChatMessageRepositoryHelper(private val mongoTemplate: MongoTemplate){
    fun findByMessageRegex(searchKeyword: String): List<PrivateChatMessage>?{
        val escapedKeyword = Pattern.quote(searchKeyword)
        val query = Query().addCriteria(Criteria.where("message").regex(escapedKeyword, "i"))
        query.with(Sort.by(Sort.Direction.DESC, "createdAt"))
        query.limit(1)
        val latestMessage = mongoTemplate.findOne(query, PrivateChatMessage::class.java) ?: return emptyList()

        val queryBefore = Query().addCriteria(
            Criteria.where("createdAt").lt(latestMessage.createdAt)
        ).with(Sort.by(Sort.Direction.DESC, "createdAt")).limit(4)

        val queryAfter = Query().addCriteria(
            Criteria.where("createdAt").gt(latestMessage.createdAt)
        ).with(Sort.by(Sort.Direction.ASC, "createdAt")).limit(4)

        val messagesBefore = mongoTemplate.find(queryBefore, PrivateChatMessage::class.java)
        val messagesAfter = mongoTemplate.find(queryAfter, PrivateChatMessage::class.java)

        val result = mutableListOf<PrivateChatMessage>()
        result.addAll(messagesBefore.reversed())
        result.add(latestMessage)
        result.addAll(messagesAfter)

        return result
    }

//    fun findByMessageContaining(searchKeyword: String): List<PrivateChatMessage>
//


}


