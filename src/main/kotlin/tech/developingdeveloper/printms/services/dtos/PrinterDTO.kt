package tech.developingdeveloper.printms.services.dtos


data class PrinterDTO(
    val name: String,
    val isAcceptingJobs: PrinterIsAcceptingJobs
) {
    enum class PrinterIsAcceptingJobs {
        NOT_ACCEPTING_JOBS, ACCEPTING_JOBS
    }
}
