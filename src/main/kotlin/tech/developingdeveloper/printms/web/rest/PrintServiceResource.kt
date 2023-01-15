package tech.developingdeveloper.printms.web.rest

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import tech.developingdeveloper.printms.services.dtos.PrinterDTO
import tech.developingdeveloper.printms.web.rest.facade.PrintServiceFacade


@RestController
class PrintServiceResource(
    private val printerServiceFacade: PrintServiceFacade
) {

    @GetMapping("/")
    fun getAllPrinters(): ResponseEntity<List<PrinterDTO>> {
        val body = printerServiceFacade.getAllPrinters()
        println("body = $body")
        return ResponseEntity.ok(body)
    }

    @GetMapping("/{printerName}")
    fun getPrinter(@PathVariable printerName: String): ResponseEntity<PrinterDTO> {
        val body = printerServiceFacade.getPrinter(printerName)
        return ResponseEntity.ok(body)
    }

    @PostMapping("/print")
    fun printPdf(
        @RequestParam files: List<MultipartFile>,
        @RequestParam printerName: String
    ) {
        printerServiceFacade.printPdf(files, printerName)
    }
}