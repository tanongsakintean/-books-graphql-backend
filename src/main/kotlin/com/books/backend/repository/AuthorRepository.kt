package com.books.backend.repository

import com.books.backend.models.entity.Author
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface AuthorRepository: ReactiveCrudRepository<Author,Long> {
    fun findByAuthorId(authorId: Long): Mono<Author>
    fun deleteAllByAuthorId(authorId: Long): Mono<Boolean>
}