package tech.developingdeveloper.printms.services.dtos

import tech.developingdeveloper.printms.entity.enum.PrinterJobAcceptanceStatus

data class PrinterDTO(
    val name: String,
    val jobAcceptanceStatus: PrinterJobAcceptanceStatus,
    val attributes: Map<String, String>
)
