package com.books.backend.repository

import com.books.backend.models.entity.Genre
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface GenreRepository: ReactiveCrudRepository<Genre,Long> {
}