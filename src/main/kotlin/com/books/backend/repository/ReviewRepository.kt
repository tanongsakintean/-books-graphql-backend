package com.books.backend.repository

import com.books.backend.models.entity.Review
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ReviewRepository: ReactiveCrudRepository<Review,Long> {
}