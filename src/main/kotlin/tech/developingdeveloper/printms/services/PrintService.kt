package tech.developingdeveloper.printms.services

import org.springframework.web.multipart.MultipartFile
import tech.developingdeveloper.printms.entity.Printer


interface PrintService {

    fun getAllPrinters(): List<Printer>
    fun getPrinter(printerName: String): Printer
    fun printPdf(files: List<MultipartFile>, printerName: String)

}