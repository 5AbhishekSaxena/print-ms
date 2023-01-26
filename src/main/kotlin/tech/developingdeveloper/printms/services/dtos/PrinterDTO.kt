package tech.developingdeveloper.printms.services.dtos

import tech.developingdeveloper.printms.entity.enum.PrinterJobAcceptanceStatus
import tech.developingdeveloper.printms.entity.enum.PrinterActivityStatus

data class PrinterDTO(
    val name: String,
    val activityStatus: PrinterActivityStatus,
    val jobAcceptanceStatus: PrinterJobAcceptanceStatus,
    val attributes: Map<String, String>
)
