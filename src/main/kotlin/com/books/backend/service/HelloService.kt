package com.books.backend.service

import com.books.backend.models.dto.AuthorDTO
import com.books.backend.models.dto.BookDTO
import com.books.backend.models.dto.response.ApiResponse
import com.books.backend.models.entity.Author
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface HelloService {
    fun sayHello(): Mono<ApiResponse<String>>
    fun getById(id: Long): Mono<Map<String, Any?>>
    fun addAuthor(body: AuthorDTO): Mono<Author>
    fun addBook(body: BookDTO): Mono<Map<String, Any?>>
    fun findAll():Flux<Map<String, Any>>
    fun save(key: String, value: String): Mono<Boolean>
    fun find(key: String): Mono<String?>
}