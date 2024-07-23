package com.books.backend.repository

import com.books.backend.models.entity.Publisher
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface PublisherRepository: ReactiveCrudRepository<Publisher,Long> {
    fun findByPublisherId(publisherId: Long): Mono<Publisher>
    fun deleteByPublisherId(publisherId: Long): Mono<Boolean>
}