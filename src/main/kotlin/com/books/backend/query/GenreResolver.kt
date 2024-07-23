package com.books.backend.query

import com.books.backend.models.dto.GenreDTO
import com.books.backend.service.GenreService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Controller
class GenreResolver {

    @Autowired
    lateinit var genreService: GenreService

    @QueryMapping
    fun genre(
        @Argument genreId: Long
    ): Mono<GenreDTO>{
        return genreService.getGenre(genreId)
    }

    @QueryMapping
    fun genres(): Flux<GenreDTO> {
        return genreService.getGenres()
    }

    @MutationMapping
    fun createGenre(
        @Argument genre: GenreDTO
    ): Mono<GenreDTO> {
        return genreService.createGenre(genre)
    }

    @MutationMapping
    fun updateGenre(
        @Argument genre: GenreDTO,
        @Argument genreId: Long
    ): Mono<GenreDTO>{
        return genreService.updateGenre(genre, genreId)
    }

    @MutationMapping
    fun deleteGenre(
        @Argument genreId: Long
    ): Mono<Boolean>{
        return genreService.deleteGenre(genreId)
    }


}