package com.books.backend.service.impl

import com.books.backend.models.dto.AuthorDTO
import com.books.backend.models.dto.errors.NotFoundException
import com.books.backend.models.dto.response.ApiResponse
import com.books.backend.models.entity.Author
import com.books.backend.repository.AuthorRepository
import com.books.backend.service.AuthorService
import com.books.backend.util.JSONUtils
import com.books.backend.util.Log
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@Service
class AuthorServiceImpl : AuthorService {
    companion object : Log()

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

    override fun getAuthors(): Flux<AuthorDTO> {
        return authorRepository.findAll().map {
            AuthorDTO(
                authorId = it.authorId,
                firstName = it.firstName,
                lastName = it.lastName,
                bio = it.bio,
                birthDay = it.birthDay
            )
        }.switchIfEmpty(Flux.just(AuthorDTO()))
    }

    override fun getAuthor(
        authorId: Long
    ): Mono<AuthorDTO> {
        return authorRepository.findByAuthorId(authorId).flatMap {
            Mono.just(
                AuthorDTO(
                    authorId = it.authorId,
                    firstName = it.firstName,
                    lastName = it.lastName,
                    bio = it.bio,
                    birthDay = it.birthDay
                )
            )
        }.switchIfEmpty(Mono.error(NotFoundException("author with authorId $authorId not found")))
            .onErrorResume { throw Exception(it) }
    }

    override fun createAuthor(author: AuthorDTO): Mono<AuthorDTO> {
        val entityToSave = Author(
            lastName = author.lastName.toString().trim(),
            firstName = author.firstName.toString().trim(),
            bio = author.bio.toString().trim(),
            birthDay = author.birthDay
        )


        return authorRepository.save(entityToSave).flatMap { exits ->
            val cacheAuthor = JSONUtils.covertToJson(
                mapOf(
                    "lastName" to exits.lastName.toString().trim(),
                    "firstName" to exits.firstName.toString().trim(),
                    "bio" to exits.bio.toString().trim(),
                    "birthDay" to exits.birthDay.toString(),
                    "authorId" to exits.authorId.toString()
                )
            )
            logger.info(cacheAuthor)
            cacheSave("authors", cacheAuthor!!).flatMap { isTrue ->
                var response = AuthorDTO()
                if (isTrue) {
                    response = AuthorDTO(
                        authorId = exits.authorId,
                        firstName = exits.firstName,
                        lastName = exits.lastName,
                        bio = exits.bio,
                        birthDay = exits.birthDay
                    )
                }
                Mono.just(response)
            }

        }.onErrorResume { throw Exception(it) }
    }

    override fun updateAuthor(author: AuthorDTO, authorId: Long): Mono<AuthorDTO> {
        return authorRepository.findByAuthorId(authorId).flatMap { exits ->
            val entityToUpdate = exits.copy(
                bio = author.bio.toString(),
                firstName = author.firstName.toString().trim(),
                lastName = author.lastName.toString().trim(),
                birthDay = author.birthDay
            )

            authorRepository.save(entityToUpdate).flatMap {
                Mono.just(
                    AuthorDTO(
                        authorId = it.authorId,
                        firstName = it.firstName,
                        lastName = it.lastName,
                        bio = it.bio,
                        birthDay = it.birthDay
                    )
                )
            }
        }.switchIfEmpty(Mono.error(NotFoundException("author with authorId $authorId not found")))
            .onErrorResume { throw Exception(it) }
    }

    override fun deleteAuthor(authorId: Long): Mono<AuthorDTO> {
        return authorRepository.findByAuthorId(authorId).flatMap {
            authorRepository.delete(it).then(Mono.defer { Mono.just(AuthorDTO()) })
                .onErrorResume { throw Exception(it) }
        }
    }


//    override fun getById(id: Long): Mono<Map<String, Any?>> {
//        return authorRepository.findById(id)
//            .flatMap {
//                bookRepository.findById(it.id!!)
//                    .flatMap { itm ->
//                        val response = mapOf(
//                            "id" to itm.id,
//                            "name" to itm.name,
//                            "pageCount" to itm.pageCount,
//                            "author" to mapOf(
//                                "id" to it.id,
//                                "firstName" to it.firstName,
//                                "lastName" to it.lastName,
//                            )
//                        )
//
//                        Mono.just(
//                            response
//                        )
//                    }
//            }
//            .onErrorResume {
//                Mono.just(emptyMap())
//            }
//    }
//
//    override fun addAuthor(body: AuthorDTO): Mono<Author> {
//        return authorRepository.save(
//            Author(
//                lastName = body.lastName,
//                firstName = body.firstName
//            )
//        )
//    }
//
//    override fun addBook(body: BookDTO): Mono<Map<String, Any?>> {
//        return bookRepository.save(
//            Book(
//                pageCount = body.pageCount,
//                name = body.name,
//                authorId = body.authorId,
//            )
//        )
//            .flatMap {
//                authorRepository.findById(it.authorId!!.toLong())
//                    .flatMap { itm ->
//                        val response = mapOf(
//                            "id" to it.id,
//                            "name" to it.name,
//                            "pageCount" to it.pageCount,
//                            "author" to mapOf(
//                                "id" to it.id,
//                                "firstName" to itm.firstName,
//                                "lastName" to itm.lastName,
//                            )
//                        )
//                        Mono.just(
//                            response
//                        )
//                    }
//            }
//    }
//
//    override fun findAll(): Flux<Map<String, Any>> {
//        return authorRepository.findAll()
//            .flatMap { author ->
//                bookRepository.findByAuthorId(author.id!!)
//                    .collectList()
//                    .map { books ->
//                        mapOf(
//                            "author" to mapOf( // author map object
//                                "id" to author.id,
//                                "firstName" to author.firstName,
//                                "lastName" to author.lastName,
//                                "books" to books.map { book -> // books map object
//                                    mapOf(
//                                        "id" to book.id,
//                                        "name" to book.name,
//                                        "pageCount" to book.pageCount,
//                                        "authorId" to book.authorId
//                                    )
//                                }
//                            )
//                        )
//                    }
//            }
//    }

    fun cacheSave(key: String, value: String): Mono<Boolean> {
        return redisTemplate.opsForValue().set(key, value)
    }

    fun cacheFind(key: String): Mono<String?> {
        return redisTemplate.opsForValue().get(key)
    }
}