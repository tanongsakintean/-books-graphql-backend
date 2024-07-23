package com.books.backend.service

import com.books.backend.models.dto.BookAuthorDTO
import com.books.backend.models.entity.BookAuthor
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface BookAuthorService {
    fun getBookAuthor(authorId: Long): Mono<BookAuthorDTO>
}