package com.books.backend.query

import com.books.backend.models.dto.BookDTO
import com.books.backend.service.BookService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Controller
class BookResolver {

    @Autowired
    lateinit var  bookService: BookService

    @QueryMapping
    fun book(
       @Argument bookId: Long
    ): Mono<BookDTO>{
        return bookService.getBook(bookId)
    }

    @QueryMapping
    fun books(): Flux<BookDTO> {
        return bookService.getBooks()
    }

    @MutationMapping
    fun createBook(
        @Argument book: BookDTO): Mono<BookDTO> {
       return bookService.createBook(book)
    }

    @MutationMapping
    fun updateBook(
        @Argument book: BookDTO,
        @Argument bookId: Long
    ): Mono<BookDTO> {
        return bookService.updateBook(book,bookId)
    }

    @MutationMapping
    fun deleteBook(
        @Argument bookId: Long
    ): Mono<Boolean>{
        return bookService.deleteBook(bookId)
    }
}