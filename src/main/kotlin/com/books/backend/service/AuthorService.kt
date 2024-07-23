package com.books.backend.service

import com.books.backend.models.dto.AuthorDTO
import com.books.backend.models.dto.response.ApiResponse
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface AuthorService {
    fun sayHello(): Mono<ApiResponse<String>>
    fun getAuthors(): Flux<AuthorDTO>
    fun getAuthor(authorId: Long): Mono<AuthorDTO>
    fun createAuthor(author: AuthorDTO): Mono<AuthorDTO>
    fun updateAuthor(author: AuthorDTO,authorId: Long): Mono<AuthorDTO>
    fun deleteAuthor(authorId: Long): Mono<Boolean>
//    fun getById(id: Long): Mono<Map<String, Any?>>
//    fun addAuthor(body: AuthorDTO): Mono<Author>
//    fun addBook(body: BookDTO): Mono<Map<String, Any?>>
//    fun findAll():Flux<Map<String, Any>>
}