package com.books.backend.repository

import com.books.backend.models.entity.BookAuthor
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface BookAuthorRepository: ReactiveCrudRepository<BookAuthor,Long> {
}