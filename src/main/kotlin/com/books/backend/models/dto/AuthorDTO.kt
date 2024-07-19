package com.books.backend.models.dto
import java.time.Instant

data class AuthorDTO(
    val authorId: Long? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val birthDay: Instant? = null,
    val bio: String? = null,
)
