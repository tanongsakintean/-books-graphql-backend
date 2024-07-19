package com.books.backend.models.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.io.Serializable
import java.sql.Timestamp

@Entity
@Table(name = "reviews")
@AllArgsConstructor
@NoArgsConstructor
@Data
data class Review(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val reviewId: Long? = null,
    val bookId: Long? = null,
    val userId: Long? = null,
    val rating: Int? = null,
    val comment: String? = null,
    val createdAt: Timestamp? = null,
): Serializable
