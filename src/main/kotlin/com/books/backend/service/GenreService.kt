package com.books.backend.service

import com.books.backend.models.dto.GenreDTO
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface GenreService {
    fun getGenre(genreId: Long): Mono<GenreDTO>
    fun getGenres(): Flux<GenreDTO>
    fun createGenre(genre: GenreDTO): Mono<GenreDTO>
    fun updateGenre(genre: GenreDTO,genreId: Long): Mono<GenreDTO>
    fun deleteGenre(genreId: Long): Mono<Boolean>
}