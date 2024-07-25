package com.books.backend.service.impl

import com.books.backend.models.dto.BookDTO
import com.books.backend.models.dto.ReviewDTO
import com.books.backend.models.dto.ReviewDetailDTO
import com.books.backend.models.dto.UserDTO
import com.books.backend.models.dto.errors.NotFoundException
import com.books.backend.models.entity.Review
import com.books.backend.repository.BookRepository
import com.books.backend.repository.ReviewRepository
import com.books.backend.repository.UserRepository
import com.books.backend.service.ReviewService
import com.books.backend.util.Log
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.sql.Timestamp
import java.time.Instant

@Service
class ReviewServiceImpl : ReviewService {
    companion object : Log()

    @Autowired
    lateinit var reviewRepository: ReviewRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var bookRepository: BookRepository

    override fun getReview(reviewId: Long): Mono<ReviewDetailDTO> {
        return reviewRepository.findByReviewId(reviewId).flatMap { review ->
            val user = userRepository.findByUserId(review.userId!!)
            val book = bookRepository.findByBookId(review.bookId!!)

            Mono.zip(user, book).flatMap {
                Mono.just(
                    ReviewDetailDTO(
                        review = ReviewDTO(
                            bookId = review.bookId,
                            userId = review.userId,
                            createdAt = review.createdAt,
                            reviewId = review.reviewId,
                            rating = review.rating,
                            comment = review.comment
                        ), book = BookDTO(
                            bookId = it.t2.bookId,
                            isbn = it.t2.isbn,
                            publicDate = it.t2.publicDate,
                            title = it.t2.title,
                            pageCount = it.t2.pageCount,
                            publisherId = it.t2.publisherId,
                            genreId = it.t2.genreId,
                            summary = it.t2.summary,
                        ), user = UserDTO(
                            createdAt = it.t1.createdAt,
                            firstName = it.t1.firstName,
                            lastName = it.t1.lastName,
                            username = it.t1.username,
                            userId = it.t1.userId,
                            email = it.t1.email,
                        )
                    )
                )
            }
        }.switchIfEmpty(Mono.error(NotFoundException("Review Not Found")))
    }

    override fun getReviews(): Flux<ReviewDetailDTO> {
        return reviewRepository.findAll().flatMap { getReview(it.reviewId!!) }
    }

    override fun createReview(review: ReviewDTO): Mono<ReviewDTO> {
        val timeStampNow = Timestamp.from(Instant.now())
        val entityToSave = Review(
            bookId = review.bookId,
            userId = review.userId,
            createdAt = timeStampNow,
            rating = review.rating,
            comment = review.comment,
        )

        return reviewRepository.save(entityToSave).flatMap {
            Mono.just(
                ReviewDTO(
                    bookId = it.bookId,
                    userId = it.userId,
                    createdAt = it.createdAt,
                    reviewId = it.reviewId,
                    rating = it.rating,
                    comment = it.comment
                )
            )
        }.onErrorResume { throw Exception(it) }
    }

    override fun updateReview(review: ReviewDTO, reviewId: Long): Mono<ReviewDTO> {
        return reviewRepository.findByReviewId(reviewId).flatMap {
            val entityToUpdate = it.copy(
                bookId = review.bookId, comment = review.comment, userId = it.userId, rating = it.rating
            )

            reviewRepository.save(entityToUpdate).flatMap { review ->
                Mono.just(
                    ReviewDTO(
                        bookId = review.bookId,
                        userId = review.userId,
                        createdAt = review.createdAt,
                        reviewId = review.reviewId,
                        rating = review.rating,
                        comment = review.comment
                    )
                )
            }.onErrorResume { throw Exception(it) }
        }
    }

    override fun deleteReview(reviewId: Long): Mono<Boolean> {
        return reviewRepository.deleteByReviewId(reviewId).flatMap { Mono.just(it) }
    }
}