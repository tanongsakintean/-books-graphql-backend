package com.books.backend.models.dto

import com.books.backend.models.dto.requests.RequestDTO
import com.books.backend.models.dto.response.ResponseDTO
import org.springframework.stereotype.Component
import org.springframework.web.context.annotation.RequestScope

@Component
@RequestScope
data class RequestResponseDTO(
    var clientIP: String?,
    var request: RequestDTO?,
    var response: ResponseDTO?
) {
    constructor() : this(null, null, null)
}