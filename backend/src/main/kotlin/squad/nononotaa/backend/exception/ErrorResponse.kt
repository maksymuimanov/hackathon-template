package squad.nononotaa.backend.exception

import org.springframework.http.HttpStatus

data class ErrorResponse(
    val code: Int,
    val status: HttpStatus,
    val message: String,
) {
    constructor(status: HttpStatus, throwable: Throwable) : this(status, throwable.message ?: throwable.toString())

    constructor(status: HttpStatus, message: String) : this(status.value(), status, message)
}
