package tech.developingdeveloper.printms.web.rest

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import tech.developingdeveloper.printms.services.FileService
import tech.developingdeveloper.printms.services.PrinterService
import tech.developingdeveloper.printms.services.dtos.PrinterDTO


@RestController
class PrintServiceResource(
    private val printerService: PrinterService,
    private val fileService: FileService
) {

    @GetMapping("/")
    fun getAllPrinters(): ResponseEntity<List<PrinterDTO>> {
        val body = printerService.getAllPrinters()
        println("body = $body")
        return ResponseEntity.ok(body)
    }

    @GetMapping("/{printerName}")
    fun getPrinter(@PathVariable printerName: String): ResponseEntity<PrinterDTO> {
        val body = if (printerName == "default")
            printerService.getDefaultPrinter()
        else
            printerService.getPrinter(printerName)
        return ResponseEntity.ok(body)
    }

    @PostMapping("/print")
    fun printPdf(
        @RequestParam files: List<MultipartFile>,
        @RequestParam printerName: String
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