package com.books.backend.models.dto

import java.sql.Timestamp

data class BookDTO(
    val bookId: Long? = null,
    val title: String? = null,
    val isbn: String? = null,
    val publicDate: Timestamp? = null,
    val publisherId: Int? = null,
    val genreId: Int? = null,
    val summary: String? = null,
    val pageCount: Int? = null,
    val authorId: List<Long>? = null
    )
