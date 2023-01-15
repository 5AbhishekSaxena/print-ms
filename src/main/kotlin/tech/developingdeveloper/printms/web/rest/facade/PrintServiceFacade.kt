package tech.developingdeveloper.printms.web.rest.facade

import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import tech.developingdeveloper.printms.services.FileService
import tech.developingdeveloper.printms.services.PrinterService
import tech.developingdeveloper.printms.services.dtos.PrinterDTO
import tech.developingdeveloper.printms.services.mapper.PrinterMapper


@Component
class PrintServiceFacade(
    private val fileService: FileService,
    private val printerService: PrinterService,
    private val printerMapper: PrinterMapper,
) {

    fun getAllPrinters(): List<PrinterDTO> {
        val printers = printerService.getAllPrinters()
        return printers.map(printerMapper::mapToDto)
    }

    fun getPrinter(printerName: String): PrinterDTO {
        val printer = printerService.getPrinter(printerName)
        return printerMapper.mapToDto(printer)
    }

    fun printPdf(
        files: List<MultipartFile>,
        printerName: String,
    ) {
        val filteredFiles = files.filterNot { it.isEmpty }

        if (filteredFiles.isEmpty())
            throw Exception("Files shouldn't be empty.")

        filteredFiles.forEach { file ->
            val tempFilePath = fileService.saveFile(file, null)
            printerService.printPdf(tempFilePath, printerName)
        }
    }
}