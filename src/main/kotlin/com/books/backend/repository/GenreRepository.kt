package com.books.backend.repository

import com.books.backend.models.entity.Genre
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface GenreRepository: ReactiveCrudRepository<Genre,Long> {
    fun findByGenreId(genreId: Long): Mono<Genre>
    fun deleteByGenreId(genreId: Long): Mono<Boolean>
}