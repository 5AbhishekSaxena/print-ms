package tech.developingdeveloper.printms.services

import javax.print.PrintService


interface PrinterService {

    fun getAllPrinters()

    fun getDefaultPrinter()

    fun findPrinter(printerName: String): PrintService

    fun printPdf(path: String, printerServiceName: String)
}