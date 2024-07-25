package com.books.backend.models.dto

data class ReviewDetailDTO(
    val review: ReviewDTO,
    val user: UserDTO,
    val book: BookDTO
)
