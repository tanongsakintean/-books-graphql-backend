package com.books.backend.repository

import com.books.backend.models.entity.User
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface UserRepository: ReactiveCrudRepository<User,Long> {
    fun findByUserId(userId: Long): Mono<User>
    fun deleteByUserId(userId: Long): Mono<Boolean>
}