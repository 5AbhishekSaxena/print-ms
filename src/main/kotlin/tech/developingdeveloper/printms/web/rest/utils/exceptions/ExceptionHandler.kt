package tech.developingdeveloper.printms.web.rest.utils.exceptions

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler(
    private val apiErrorFactory: ApiErrorFactory
) {

    @org.springframework.web.bind.annotation.ExceptionHandler
    fun generalExceptionHandler(excpetion: Exception): ResponseEntity<ApiError> {
        val apiError = apiErrorFactory.create(exception = excpetion)
        return ResponseEntity(apiError, apiError.status)
    }
}