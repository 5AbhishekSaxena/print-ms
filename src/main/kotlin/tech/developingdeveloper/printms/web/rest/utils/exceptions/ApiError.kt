package tech.developingdeveloper.printms.web.rest.utils.exceptions

import org.springframework.http.HttpStatus
import java.time.LocalDateTime


data class ApiError(
    val message: String,
    val status: HttpStatus,
    val code: Int,
    val timestamp: LocalDateTime
)
