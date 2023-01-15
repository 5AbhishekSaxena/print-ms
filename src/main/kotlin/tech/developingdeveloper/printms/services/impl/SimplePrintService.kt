package tech.developingdeveloper.printms.services.impl

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import tech.developingdeveloper.printms.entity.Printer
import tech.developingdeveloper.printms.services.FileService
import tech.developingdeveloper.printms.services.PrintService
import tech.developingdeveloper.printms.services.PrinterService

@Service
class SimplePrintService(
    private val fileService: FileService,
    private val printerService: PrinterService
) : PrintService {
    override fun getAllPrinters(): List<Printer> {
        return printerService.getAllPrinters()
    }

    override fun getPrinter(printerName: String): Printer {
        return printerService.getPrinter(printerName)
    }

    override fun printPdf(files: List<MultipartFile>, printerName: String) {
        val filteredFiles = files.filterNot { it.isEmpty }

        if (filteredFiles.isEmpty())
            throw Exception("Files shouldn't be empty.")

        filteredFiles.forEach { file ->
            val tempFilePath = fileService.saveFile(file, null)
            printerService.printPdf(tempFilePath, printerName)
        }
    }
}