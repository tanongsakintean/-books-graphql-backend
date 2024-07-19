package com.books.backend.models.entity

import jakarta.persistence.Entity
import org.springframework.data.relational.core.mapping.Table
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import org.springframework.data.annotation.Id

@Entity
@Table(name = "book_authors")
@NoArgsConstructor
@AllArgsConstructor
@Data
data class BookAuthor(
   @Id
   val bookAuthorId: Long? = null,
   val bookId: Int? = null,
   val authorId: Int? = null,
)
