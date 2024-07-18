package com.books.backend.query

import com.books.backend.models.dto.AuthorDTO
import com.books.backend.models.dto.BookDTO
import com.books.backend.models.entity.Author
import com.books.backend.service.HelloService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Controller
class HelloResolver {

    @Autowired
    lateinit var helloService: HelloService

    @QueryMapping
    fun bookById(
        @Argument id: Long
    ): Mono<Map<String, Any?>> {
        return helloService.getById(id)
    }

    @QueryMapping
    fun books():Flux<Map<String, Any>> {
        return helloService.findAll()
    }

    @MutationMapping
    fun createAuthor(
        @Argument input: AuthorDTO,
    ): Mono<Author> {
        return helloService.addAuthor(input)
    }

    @MutationMapping
    fun createBook(
        @Argument input: BookDTO
    ): Mono<Map<String, Any?>> {
        return helloService.addBook(input)
    }
}