package com.books.backend.query

import com.books.backend.models.dto.PublisherDTO
import com.books.backend.service.PublisherService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Controller
class PublisherResolver {
    @Autowired
    lateinit var publisherService: PublisherService
    
    @QueryMapping
    fun publisher(
        @Argument publisherId: Long
    ): Mono<PublisherDTO>{
        return publisherService.getPublisher(publisherId)
    }
    
    @QueryMapping
    fun publishers(): Flux<PublisherDTO> {
        return publisherService.getPublishers()
    }
    
    @MutationMapping
    fun createPublisher(
        @Argument publisher: PublisherDTO
    ): Mono<PublisherDTO> {
        return publisherService.createPublisher(publisher)
    }
    
    @MutationMapping
    fun updatePublisher(
       @Argument publisher: PublisherDTO,
       @Argument publisherId: Long
    ): Mono<PublisherDTO> {
        return publisherService.updatePublisher(publisher, publisherId)
    }

    @MutationMapping
    fun deletePublisher(@Argument publisherId: Long): Mono<Boolean> {
        return publisherService.deletePublisher(publisherId)
    }
}