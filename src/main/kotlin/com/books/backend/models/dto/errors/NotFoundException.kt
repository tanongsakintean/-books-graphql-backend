package com.books.backend.models.dto.errors

data class NotFoundException(
    override val message: String? = "User not found"
): RuntimeException(message)
