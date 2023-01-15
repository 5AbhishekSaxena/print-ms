package tech.developingdeveloper.printms.services.mapper

import org.springframework.stereotype.Service
import tech.developingdeveloper.printms.entity.Printer
import tech.developingdeveloper.printms.services.dtos.PrinterDTO


@Service
class PrinterMapper {
    fun mapToDto(printer: Printer): PrinterDTO {
        return PrinterDTO(
            name = printer.name,
            jobAcceptanceStatus = printer.jobAcceptanceStatus,
            attributes = printer.attributes
        )
    }

    fun mapToDto(printers: List<Printer>): List<PrinterDTO> {
        return printers.map(::mapToDto)
    }
}