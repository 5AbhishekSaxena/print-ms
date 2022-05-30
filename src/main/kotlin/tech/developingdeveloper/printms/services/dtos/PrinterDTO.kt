package tech.developingdeveloper.printms.services.dtos

import com.fasterxml.jackson.annotation.JsonProperty


data class PrinterDTO(
    val name: String,
    @field:JsonProperty("isAcceptingJobs")
    val isAcceptingJobs: PrinterIsAcceptingJobs,
    val attributes: Map<String, String>
) {
    enum class PrinterIsAcceptingJobs {
        NOT_ACCEPTING_JOBS, ACCEPTING_JOBS
    }
}
