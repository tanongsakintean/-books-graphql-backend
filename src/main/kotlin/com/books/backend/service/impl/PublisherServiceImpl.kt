package com.books.backend.service.impl

import com.books.backend.models.dto.PublisherDTO
import com.books.backend.models.dto.errors.NotFoundException
import com.books.backend.models.entity.Publisher
import com.books.backend.repository.PublisherRepository
import com.books.backend.service.PublisherService
import com.books.backend.util.Log
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class PublisherServiceImpl : PublisherService {
    companion object : Log()

    @Autowired
    lateinit var publisherRepository: PublisherRepository

    override fun getPublishers(): Flux<PublisherDTO> {
        return publisherRepository.findAll().flatMap {
            Flux.just(
                PublisherDTO(
                    name = it.name,
                    publisherId = it.publisherId,
                    address = it.address,
                    website = it.website,
                )
            )
        }.onErrorResume { throw Exception(it) }
    }

    override fun getPublisher(publisherId: Long): Mono<PublisherDTO> {
        return publisherRepository.findByPublisherId(publisherId).flatMap {
            Mono.just(
                PublisherDTO(
                    name = it.name, publisherId = it.publisherId, address = it.address, website = it.website
                )
            )
        }.switchIfEmpty(Mono.error(NotFoundException("No publisher found for id $publisherId")))
    }

    override fun createPublisher(publisher: PublisherDTO): Mono<PublisherDTO> {
        val entityToSave = Publisher(
            name = publisher.name,
            publisherId = publisher.publisherId,
            address = publisher.address,
            website = publisher.website,
        )

        return publisherRepository.save(entityToSave).flatMap {
            Mono.just(
                PublisherDTO(
                    name = it.name, publisherId = it.publisherId, address = it.address, website = it.website
                )
            )
        }.onErrorResume { throw Exception(it) }
    }

    override fun updatePublisher(publisher: PublisherDTO, publisherId: Long): Mono<PublisherDTO> {
        return publisherRepository.findByPublisherId(publisherId).flatMap {
            val entityToUpdate = it.copy(
                address = publisher.address,
                website = publisher.website,
                name = publisher.name,
            )

            publisherRepository.save(entityToUpdate).flatMap { itm->
                Mono.just(
                    PublisherDTO(
                        name = itm.name, publisherId = itm.publisherId, address = itm.address, website = itm.website
                    )
                )
            }.onErrorResume { throw Exception(it) }
        }.switchIfEmpty(Mono.error(NotFoundException("No publisher found for id $publisherId")))
    }

    override fun deletePublisher(publisherId: Long): Mono<Boolean> {
        return publisherRepository.deleteByPublisherId(publisherId).flatMap {
            Mono.just(it)
        }.onErrorResume { throw Exception(it) }
    }
}