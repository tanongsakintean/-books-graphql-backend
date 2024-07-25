package com.books.backend.repository

import com.books.backend.models.entity.Review
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface ReviewRepository: ReactiveCrudRepository<Review,Long> {
    fun findByReviewId(reviewId: Long): Mono<Review>
    fun deleteByReviewId(reviewId: Long): Mono<Boolean>
}