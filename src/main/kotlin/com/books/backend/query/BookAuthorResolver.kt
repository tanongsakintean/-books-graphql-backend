package com.books.backend.query

import com.books.backend.models.dto.BookAuthorDTO
import com.books.backend.service.BookAuthorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Controller
class BookAuthorResolver {

    @Autowired
    lateinit var bookAuthorService: BookAuthorService

    @QueryMapping
    fun bookAuthor(
        @Argument authorId: Long
    ): Mono<BookAuthorDTO> {
        return bookAuthorService.getBookAuthor(authorId)
    }

    @QueryMapping
    fun bookAuthors(): Flux<BookAuthorDTO> {
        return bookAuthorService.getBookAuthors()
    }
}