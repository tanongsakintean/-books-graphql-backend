package com.books.backend.models.dto

import java.sql.Timestamp

data class ReviewDTO(
    val reviewId: Long? = null,
    val bookId: Long? = null,
    val userId: Long? = null,
    val rating: Int? = null,
    val comment: String? = null,
    val createdAt: Timestamp? = null,
)
