package com.books.backend.models.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
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
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   val bookAuthorId: Long? = null,
   val bookId: Long? = null,
   val authorId: Long? = null,
)
