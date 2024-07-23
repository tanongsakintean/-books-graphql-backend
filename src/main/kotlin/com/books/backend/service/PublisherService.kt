package com.books.backend.service

import com.books.backend.models.dto.PublisherDTO
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface PublisherService {
    fun getPublishers(): Flux<PublisherDTO>
    fun getPublisher(publisherId: Long): Mono<PublisherDTO>
    fun createPublisher(publisher: PublisherDTO): Mono<PublisherDTO>
    fun updatePublisher(publisher: PublisherDTO,publisherId: Long): Mono<PublisherDTO>
    fun deletePublisher(publisherId: Long): Mono<Boolean>
}