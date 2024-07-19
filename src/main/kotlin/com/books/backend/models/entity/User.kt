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
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userId: Long? = null,
    val username: String? = null,
    val passwordHash: String? = null,
    val email: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val createdAt: Timestamp? = null,
): Serializable
