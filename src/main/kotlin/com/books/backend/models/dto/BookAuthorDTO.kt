package com.books.backend.models.dto

data class BookAuthorDTO(
    val books: List<BookPublisherDTO?>,
    val author: AuthorDTO?
)
