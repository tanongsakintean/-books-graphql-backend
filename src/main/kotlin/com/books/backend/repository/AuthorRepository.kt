package com.books.backend.repository

import com.books.backend.models.entity.Author
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthorRepository: ReactiveCrudRepository<Author,Long> {
}