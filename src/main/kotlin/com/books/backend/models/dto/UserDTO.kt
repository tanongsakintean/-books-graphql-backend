package com.books.backend.models.dto

import java.sql.Timestamp

data class UserDTO(
    val userId: Long? = null,
    val username: String? = null,
    val passwordHash: String? = null,
    val email: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val createdAt: Timestamp? = null,
)
