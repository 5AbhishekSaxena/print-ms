package tech.developingdeveloper.printms.services

import tech.developingdeveloper.printms.entity.Printer


interface PrinterService {

    fun getAllPrinters(): List<Printer>

    fun getDefaultPrinter(): Printer

    fun getPrinter(printerName: String): Printer

    fun isPrinterPresent(printerName: String): Boolean

    fun printPdf(path: String, printerServiceName: String)
}