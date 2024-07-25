package com.books.backend.models.dto

data class BookDetailDTO(
    val book: BookDTO,
    val publisher: PublisherDTO,
    val genre: GenreDTO
)
