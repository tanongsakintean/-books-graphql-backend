package com.books.backend.repository

import com.books.backend.models.entity.BookAuthor
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface BookAuthorRepository: ReactiveCrudRepository<BookAuthor,Long> {
    fun findByBookId(bookId:Long): Flux<BookAuthor>
    fun findByAuthorId(authorId:Long): Flux<BookAuthor>
    fun deleteByBookId(bookId:Long): Mono<Boolean>
}