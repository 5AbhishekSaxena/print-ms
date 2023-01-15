package tech.developingdeveloper.printms.web.rest.facade

import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import tech.developingdeveloper.printms.services.PrintService
import tech.developingdeveloper.printms.services.dtos.PrinterDTO
import tech.developingdeveloper.printms.services.mapper.PrinterMapper


@Component
class PrintServiceFacade(
    private val printService: PrintService,
    private val printerMapper: PrinterMapper,
) {

    fun getAllPrinters(): List<PrinterDTO> {
        val printers = printService.getAllPrinters()
        return printerMapper.mapToDto(printers)
    }

    fun getPrinter(printerName: String): PrinterDTO {
        val printer = printService.getPrinter(printerName)
        return printerMapper.mapToDto(printer)
    }

    fun printPdf(
        files: List<MultipartFile>,
        printerName: String,
    ) {
        printService.printPdf(files, printerName)
    }
}