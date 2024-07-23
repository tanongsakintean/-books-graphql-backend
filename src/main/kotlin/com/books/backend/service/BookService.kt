package com.books.backend.service

import com.books.backend.models.dto.BookDTO
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface BookService {
    fun getBook(bookId: Long): Mono<BookDTO>
    fun getBooks(): Flux<BookDTO>
    fun createBook(book: BookDTO): Mono<BookDTO>
    fun updateBook(book: BookDTO,bookId: Long): Mono<BookDTO>
    fun deleteBook(bookId: Long): Mono<Boolean>
}