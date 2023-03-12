package tech.developingdeveloper.printms.web.rest.utils.exceptions

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ResourceAdvice(
    private val apiErrorFactory: ApiErrorFactory
) {

    @ExceptionHandler(Exception::class)
    fun generalExceptionHandler(exception: Exception): ResponseEntity<ApiError> {
        val apiError = apiErrorFactory.create(exception = exception)
        return ResponseEntity(apiError, apiError.status)
    }
}