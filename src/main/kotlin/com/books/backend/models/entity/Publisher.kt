package com.books.backend.models.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import org.springframework.data.relational.core.mapping.Table
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import org.springframework.data.annotation.Id
import java.io.Serializable

@Entity
@Table(name = "publishers")
@NoArgsConstructor
@AllArgsConstructor
@Data
data class Publisher(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val publisherId: Long? = null,
    val name: String? = null,
    val address: String? = null,
    val website: String? = null,
): Serializable
