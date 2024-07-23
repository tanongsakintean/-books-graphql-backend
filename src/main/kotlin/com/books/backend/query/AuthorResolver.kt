package com.books.backend.query

import com.books.backend.models.dto.AuthorDTO
import com.books.backend.service.AuthorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Controller
class AuthorResolver {

    @Autowired
    lateinit var bookService: AuthorService

    @QueryMapping
    fun authors(): Flux<AuthorDTO> {
        return bookService.getAuthors()
    }

    @QueryMapping
    fun author(
       @Argument authorId: Long
    ): Mono<AuthorDTO> {
        return bookService.getAuthor(authorId)
    }

    @MutationMapping
    fun createAuthor(
        @Argument author: AuthorDTO
    ): Mono<AuthorDTO> {
        return bookService.createAuthor(author)
    }

    @MutationMapping
    fun updateAuthor(
        @Argument author: AuthorDTO,
        @Argument authorId: Long
    ): Mono<AuthorDTO> {
        return bookService.updateAuthor(author, authorId)
    }

    @MutationMapping
    fun deleteAuthor(
        @Argument authorId: Long
    ): Mono<Boolean> {
       return bookService.deleteAuthor(authorId)
    }

//
//    @MutationMapping
//    fun createAuthor(
//        @Argument input: AuthorDTO,
//    ): Mono<Author> {
//        return helloService.addAuthor(input)
//    }
//
//    @MutationMapping
//    fun createBook(
//        @Argument input: BookDTO
//    ): Mono<Map<String, Any?>> {
//        return helloService.addBook(input)
//    }
}