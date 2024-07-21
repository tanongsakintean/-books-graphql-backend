package com.books.backend.service.impl

import com.books.backend.models.dto.UserDTO
import com.books.backend.models.dto.errors.NotFoundException
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
            .switchIfEmpty(Mono.error(NotFoundException("User with userId $userId not found")))
            .onErrorResume { Mono.error(it) }
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
            .onErrorResume { throw Exception(it) }
    }

    override fun updateUser(user: UserDTO, userId: Long): Mono<UserDTO> {
        return userRepository.findByUserId(userId)
            .flatMap {
                userRepository.save(
                    it.copy(
                        username = user.username.toString().trim(),
                        lastName = user.lastName.toString().trim(),
                        firstName = user.firstName.toString().trim(),
                        email = user.email.toString().trim(),
                    )
                )
                    .flatMap { itm ->
                        Mono.just(
                            UserDTO(
                                userId = itm.userId,
                                username = itm.username,
                                email = itm.email,
                                firstName = itm.firstName,
                                lastName = itm.lastName,
                                createdAt = itm.createdAt
                            )
                        )
                    }
                    .onErrorResume { throw Exception(it) }
            }
            .switchIfEmpty(Mono.error(NotFoundException("User with userId $userId not found")))
    }

    override fun deleteUser(userId: Long): Mono<UserDTO> {
        return userRepository.deleteById(userId)
            .then(Mono.defer { Mono.just(UserDTO()) })
            .onErrorResume { throw Exception(it) }
    }
}