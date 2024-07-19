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
import java.time.Instant

@Entity
@Table(name = "authors")
@AllArgsConstructor
@NoArgsConstructor
@Data
data class Author(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val authorId: Long? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val birthDay: Instant? = null,
    val bio: String? = null,
): Serializable
