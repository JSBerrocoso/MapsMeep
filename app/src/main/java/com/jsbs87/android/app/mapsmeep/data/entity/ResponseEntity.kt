package com.jsbs87.android.app.mapsmeep.data.entity

class ResponseEntity<T>(
    val response: T?,
    val error: ErrorResponse?
)

class ErrorResponse(
    val code: Int?,
    val message: String? = ""
)