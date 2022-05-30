package tech.developingdeveloper.printms.services

import tech.developingdeveloper.printms.services.dtos.PrinterDTO
import javax.print.PrintService


interface PrinterService {

    fun getAllPrinters(): List<PrinterDTO>

    fun getDefaultPrinter()

    fun findPrinter(printerName: String): PrintService

    fun printPdf(path: String, printerServiceName: String)
}