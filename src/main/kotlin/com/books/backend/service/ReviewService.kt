package com.books.backend.service

import com.books.backend.models.dto.ReviewDTO
import com.books.backend.models.dto.ReviewDetailDTO
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface ReviewService {
    fun getReview(reviewId: Long): Mono<ReviewDetailDTO>
    fun getReviews(): Flux<ReviewDetailDTO>
    fun createReview(review: ReviewDTO): Mono<ReviewDTO>
    fun updateReview(review: ReviewDTO, reviewId: Long): Mono<ReviewDTO>
    fun deleteReview(reviewId: Long): Mono<Boolean>
}