package com.books.backend.config

import com.books.backend.models.dto.errors.NotFoundException
import com.books.backend.util.Log
import graphql.GraphQLError
import graphql.GraphqlErrorBuilder
import graphql.schema.DataFetchingEnvironment
import org.springframework.graphql.execution.DataFetcherExceptionResolver
import org.springframework.graphql.execution.ErrorType
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class GraphQLExceptionHandler : DataFetcherExceptionResolver {
    companion object : Log()

    override fun resolveException(ex: Throwable, env: DataFetchingEnvironment): Mono<List<GraphQLError>> {
        val error = when (ex) {
            is NotFoundException -> GraphqlErrorBuilder.newError(env)
                .message(ex.message ?: "user not found!")
                .errorType(ErrorType.NOT_FOUND)
//                .location(env.field.sourceLocation)
                .path(env.executionStepInfo.path)
                .build()

            else -> GraphqlErrorBuilder.newError(env)
                .message(ex.message ?: "An unknown error occurred!")
                .errorType(ErrorType.INTERNAL_ERROR)
//                .extensions(mapOf("code" to ErrorType.INTERNAL_ERROR))
                .build()

        }
        return Mono.just(listOf(error))
    }
}