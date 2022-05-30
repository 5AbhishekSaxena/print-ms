package tech.developingdeveloper.printms.web.rest

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import tech.developingdeveloper.printms.services.PrinterService
import tech.developingdeveloper.printms.services.dtos.PrinterDTO


@RestController
class PrintServiceResource(
    private val printerService: PrinterService
) {

    @GetMapping("/")
    fun getAllPrinters(): ResponseEntity<List<PrinterDTO>> {
        val body = printerService.getAllPrinters()
        println("body = $body")
        return ResponseEntity.ok(body)
    }

//    @GetMapping("/default")
//    fun getDefaultPrinter(): ResponseEntity<PrinterDTO> {
//        val body = printerService.getDefaultPrinter()
//        return ResponseEntity.ok(body)
//    }

    @GetMapping("/{printerName}")
    fun getPrinter(@PathVariable printerName: String): ResponseEntity<PrinterDTO> {
        val body = if (printerName == "default")
            printerService.getDefaultPrinter()
        else
            printerService.getPrinter(printerName)
        return ResponseEntity.ok(body)
    }
}