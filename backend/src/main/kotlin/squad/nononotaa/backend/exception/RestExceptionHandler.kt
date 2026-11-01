package squad.nononotaa.backend.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class RestExceptionHandler {
    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(ex: NotFoundException) = ErrorResponse(HttpStatus.NOT_FOUND, ex)

    @ExceptionHandler(ValidationException::class)
    fun handleBusinessException(ex: ValidationException) = ErrorResponse(HttpStatus.UNPROCESSABLE_CONTENT, ex)

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception) = ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex)
}
