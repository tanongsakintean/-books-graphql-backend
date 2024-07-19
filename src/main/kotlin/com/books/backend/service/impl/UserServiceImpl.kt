package com.books.backend.service.impl

import com.books.backend.models.dto.UserDTO
import com.books.backend.models.entity.User
import com.books.backend.repository.UserRepository
import com.books.backend.service.UserService
import com.books.backend.util.Log
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.sql.Timestamp
import java.time.Instant

@Service
class UserServiceImpl() : UserService {
    companion object : Log()

    @Autowired
    lateinit var userRepository: UserRepository


    override fun getUser(userId: Long): Mono<UserDTO> {
        return userRepository.findByUserId(userId)
            .flatMap {
                Mono.just(
                    UserDTO(
                        userId = it.userId,
                        username = it.username,
                        email = it.email,
                        firstName = it.firstName,
                        lastName = it.lastName,
                        createdAt = it.createdAt
                    )
                )
            }
            .switchIfEmpty(Mono.just(UserDTO()))
    }

    override fun getUsers(): Flux<UserDTO> {
        return userRepository.findAll()
            .flatMap {
                Flux.just(
                    UserDTO(
                        userId = it.userId,
                        username = it.username,
                        email = it.email,
                        firstName = it.firstName,
                        lastName = it.lastName,
                        createdAt = it.createdAt
                    )
                )
            }
            .switchIfEmpty(Flux.just(UserDTO()))
    }

    override fun createUser(user: UserDTO): Mono<UserDTO> {
        val timestampNow = Timestamp.from(Instant.now())

        val entityToSave = User(
            userId = user.userId,
            username = user.username,
            email = user.email,
            firstName = user.firstName,
            lastName = user.lastName,
            createdAt = timestampNow,
            passwordHash = user.passwordHash // solution password
        )

        return userRepository.save(entityToSave)
            .flatMap {
                Mono.just(
                    UserDTO(
                        userId = it.userId,
                        username = it.username,
                        email = it.email,
                        firstName = it.firstName,
                        lastName = it.lastName,
                        createdAt = it.createdAt
                    )
                )
            }
    }
}