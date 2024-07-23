package com.books.backend.service.impl

import com.books.backend.models.dto.GenreDTO
import com.books.backend.models.dto.errors.NotFoundException
import com.books.backend.models.entity.Genre
import com.books.backend.repository.GenreRepository
import com.books.backend.service.GenreService
import com.books.backend.util.Log
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class GenreServiceImpl : GenreService {
    companion object : Log()

    @Autowired
    lateinit var genreRepository: GenreRepository

    override fun getGenre(genreId: Long): Mono<GenreDTO> {
        return genreRepository.findByGenreId(genreId)
            .flatMap {
                Mono.just(
                    GenreDTO(
                        genreId = it.genreId,
                        name = it.name,
                    )
                )
            }
            .switchIfEmpty(Mono.error(NotFoundException("Genre $genreId Not found")))
    }

    override fun getGenres(): Flux<GenreDTO> {
        return genreRepository.findAll()
            .flatMap {
                Flux.just(
                    GenreDTO(
                        genreId = it.genreId,
                        name = it.name
                    )
                )
            }
    }

    override fun createGenre(genre: GenreDTO): Mono<GenreDTO> {
        /// solution check if genre same in DB

        val entityToSave = Genre(
            name = genre.name
        )

        return genreRepository.save(entityToSave)
            .flatMap {
                Mono.just(
                    GenreDTO(
                        genreId = it.genreId,
                        name = it.name,
                    )
                )
            }
            .onErrorResume { throw Exception(it) }
    }

    override fun updateGenre(genre: GenreDTO, genreId: Long): Mono<GenreDTO> {
        return genreRepository.findByGenreId(genreId)
            .flatMap {
                val entityToUpdate = it.copy(
                    name = genre.name.toString().trim(),
                )

                genreRepository.save(entityToUpdate)
                    .flatMap { itm->
                        Mono.just(
                            GenreDTO(
                                genreId = itm.genreId,
                                name = itm.name,
                            )
                        )
                    }
                    .onErrorResume { throw Exception(it) }
            }
    }

    override fun deleteGenre(genreId: Long): Mono<Boolean> {
        return genreRepository.deleteByGenreId(genreId)
            .flatMap {
                Mono.just(it)
            }
            .onErrorResume { throw Exception(it) }
    }
}