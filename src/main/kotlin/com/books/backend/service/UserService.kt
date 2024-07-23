package com.books.backend.service

import com.books.backend.models.dto.UserDTO
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface UserService {
    fun getUser(userId:Long): Mono<UserDTO>
    fun getUsers(): Flux<UserDTO>
    fun createUser(user: UserDTO): Mono<UserDTO>
    fun updateUser(user: UserDTO,userId: Long): Mono<UserDTO>
    fun deleteUser(userId: Long): Mono<Boolean>
}