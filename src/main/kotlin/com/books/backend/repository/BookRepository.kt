package com.books.backend.repository

import com.books.backend.models.entity.Book
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface BookRepository: ReactiveCrudRepository<Book, Long> {
    fun findByBookId(bookId: Long): Mono<Book>
    fun deleteByBookId(bookId: Long): Mono<Boolean>
}