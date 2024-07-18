package com.books.backend.models.dto

data class ApiDTO(
    var serviceName: String?,
    var retries: Int?
) {
    constructor() : this(null, null)
}

