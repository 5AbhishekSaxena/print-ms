package tech.developingdeveloper.printms.web.rest.utils.exceptions

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class ApiErrorFactory {

    fun create(exception: Exception, status: HttpStatus = HttpStatus.BAD_REQUEST): ApiError {
        val message = exception.message ?: "Something went wrong."
        return ApiError(
            message = message,
            status = status,
            code = status.value(),
            timestamp = LocalDateTime.now()
        )
    }
}