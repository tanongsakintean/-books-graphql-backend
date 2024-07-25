package com.books.backend.models.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.annotation.Id
import java.io.Serializable
import java.sql.Timestamp

@Entity
@Table(name = "books")
@AllArgsConstructor
@NoArgsConstructor
@Data
data class Book(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val bookId: Long? = null,
    val title: String? = null,
    val isbn: String? = null,
    val publicDate: Timestamp? = null,
    val publisherId: Long? = null,
    val genreId: Long? = null,
    val summary: String? = null,
    val pageCount: Long? = null,
): Serializable
