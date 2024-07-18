package com.books.backend.service.impl

import com.books.backend.models.dto.AuthorDTO
import com.books.backend.models.dto.BookDTO
import com.books.backend.models.dto.response.ApiResponse
import com.books.backend.models.entity.Author
import com.books.backend.models.entity.Book
import com.books.backend.repository.AuthorRepository
import com.books.backend.repository.BookRepository
import com.books.backend.service.HelloService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@Service
class HelloServiceImpl : HelloService {

    @Autowired
    lateinit var bookRepository: BookRepository

    @Autowired
    lateinit var authorRepository: AuthorRepository

    @Autowired
    @Qualifier("reactiveRedisTemplate")
    lateinit var redisTemplate: ReactiveRedisTemplate<String, String>

    override fun sayHello(): Mono<ApiResponse<String>> {
        return Mono.just(
            ApiResponse(
                HttpStatus.OK.value().toString(),
                "SUCCESS",
                "Hello World!",
            )
        )
    }

    override fun getById(id: Long): Mono<Map<String, Any?>> {
        return authorRepository.findById(id)
            .flatMap {
                bookRepository.findById(it.id!!)
                    .flatMap { itm ->
                        val response = mapOf(
                            "id" to itm.id,
                            "name" to itm.name,
                            "pageCount" to itm.pageCount,
                            "author" to mapOf(
                                "id" to it.id,
                                "firstName" to it.firstName,
                                "lastName" to it.lastName,
                            )
                        )

                        Mono.just(
                            response
                        )
                    }
            }
            .onErrorResume {
                Mono.just(emptyMap())
            }
    }

    override fun addAuthor(body: AuthorDTO): Mono<Author> {
        return authorRepository.save(
            Author(
                lastName = body.lastName,
                firstName = body.firstName
            )
        )
    }

    override fun addBook(body: BookDTO): Mono<Map<String, Any?>> {
        return bookRepository.save(
            Book(
                pageCount = body.pageCount,
                name = body.name,
                authorId = body.authorId,
            )
        )
            .flatMap {
                authorRepository.findById(it.authorId!!.toLong())
                    .flatMap { itm ->
                        val response = mapOf(
                            "id" to it.id,
                            "name" to it.name,
                            "pageCount" to it.pageCount,
                            "author" to mapOf(
                                "id" to it.id,
                                "firstName" to itm.firstName,
                                "lastName" to itm.lastName,
                            )
                        )
                        Mono.just(
                            response
                        )
                    }
            }
    }

    override fun findAll(): Flux<Map<String, Any>> {
        return authorRepository.findAll()
            .flatMap { author ->
                bookRepository.findByAuthorId(author.id!!)
                    .collectList()
                    .map { books ->
                        mapOf(
                            "author" to mapOf( // author map object
                                "id" to author.id,
                                "firstName" to author.firstName,
                                "lastName" to author.lastName,
                                "books" to books.map { book -> // books map object
                                    mapOf(
                                        "id" to book.id,
                                        "name" to book.name,
                                        "pageCount" to book.pageCount,
                                        "authorId" to book.authorId
                                    )
                                }
                            )
                        )
                    }
            }
    }

    override fun save(key: String, value: String): Mono<Boolean> {
        return redisTemplate.opsForValue().set(key, value)
    }

    override fun find(key: String): Mono<String?> {
        return redisTemplate.opsForValue().get(key)
    }
}