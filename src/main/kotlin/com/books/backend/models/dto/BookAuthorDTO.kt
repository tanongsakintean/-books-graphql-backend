package com.books.backend.models.dto

data class BookAuthorDTO(
    val books: List<BookDetailDTO?>,
    val author: AuthorDTO?
)
