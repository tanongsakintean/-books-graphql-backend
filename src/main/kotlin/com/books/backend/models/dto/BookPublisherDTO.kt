package com.books.backend.models.dto

data class BookPublisherDTO(
    val book: BookDTO,
    val publisher: PublisherDTO,
    val genre: GenreDTO
)
