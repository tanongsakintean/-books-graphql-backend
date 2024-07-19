package com.books.backend.repository

import com.books.backend.models.entity.Publisher
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PublisherRepository: ReactiveCrudRepository<Publisher,Long> {
}