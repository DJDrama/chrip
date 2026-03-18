package com.dj.www.chrip.infra.database.repository

import com.dj.www.chrip.domain.type.ChatId
import com.dj.www.chrip.domain.type.UserId
import com.dj.www.chrip.infra.database.entity.ChatEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ChatRepository : JpaRepository<ChatEntity, ChatId> {
    @Query(
        """
        SELECT c
        FROM ChatEntity c
        LEFT JOIN FETCH c.participants
        LEFT JOIN FETCH c.creator
        WHERE c.id = :id
        AND EXISTS (
            SELECT 1 FROM c.participants p
            WHERE p.userId = :userId
        )
    """
    )
    fun findChatById(id: ChatId, userId: UserId): ChatEntity?

    // eager loading data with left join fetch -> all required info is gathered in a single query (boosting efficiency)
    @Query(
        """
        SELECT c
        FROM ChatEntity c
        LEFT JOIN FETCH c.participants
        LEFT JOIN FETCH c.creator
        WHERE EXISTS (
            SELECT 1 FROM c.participants p
            WHERE p.userId = :userId
        )
    """
    )
    fun findAllByUserId(userId: UserId): List<ChatEntity>
}