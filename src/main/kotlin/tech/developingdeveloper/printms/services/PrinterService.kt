package tech.developingdeveloper.printms.services

import tech.developingdeveloper.printms.services.dtos.PrinterDTO


interface PrinterService {

    fun getAllPrinters(): List<PrinterDTO>

    fun getDefaultPrinter(): PrinterDTO

    fun getPrinter(printerName: String): PrinterDTO

    fun isPrinterPresent(printerName: String): Boolean

    fun printPdf(path: String, printerServiceName: String)
}