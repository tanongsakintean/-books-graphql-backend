package com.books.backend.query

import com.books.backend.models.dto.ReviewDTO
import com.books.backend.models.dto.ReviewDetailDTO
import com.books.backend.service.ReviewService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Controller
class ReviewResolver {
    @Autowired
    lateinit var reviewService: ReviewService

    @QueryMapping
    fun review(
        @Argument reviewId: Long
    ): Mono<ReviewDetailDTO> {
        return reviewService.getReview(reviewId)
    }

    @QueryMapping
    fun reviews(): Flux<ReviewDetailDTO> {
        return reviewService.getReviews()
    }

    @MutationMapping
    fun createReview(
        @Argument review: ReviewDTO
    ): Mono<ReviewDTO> {
        return reviewService.createReview(review)
    }

    @MutationMapping
    fun updateReview(
        @Argument review: ReviewDTO, @Argument reviewId: Long
    ): Mono<ReviewDTO> {
        return reviewService.updateReview(review, reviewId)
    }

    @MutationMapping
    fun deleteReview(
        @Argument reviewId: Long
    ): Mono<Boolean> {
        return reviewService.deleteReview(reviewId)
    }


}