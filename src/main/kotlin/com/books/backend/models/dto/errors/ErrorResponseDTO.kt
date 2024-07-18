package com.books.backend.models.dto.errors

data class ErrorResponseDTO(
    val code: Int,
    val message: String?,
    val details: MutableList<Map<String, Any>>?
)