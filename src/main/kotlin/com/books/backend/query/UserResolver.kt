package com.books.backend.query

import com.books.backend.models.dto.UserDTO
import com.books.backend.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Controller
class UserResolver {
    @Autowired
    lateinit var userService: UserService

    @QueryMapping
    fun user(
        @Argument userId: Long
    ): Mono<UserDTO>{
        return userService.getUser(userId)
    }

    @QueryMapping
    fun users(): Flux<UserDTO> {
        return userService.getUsers()
    }

    @MutationMapping
    fun createUser(
        @Argument user: UserDTO
    ): Mono<UserDTO> {
        return userService.createUser(user)
    }


}