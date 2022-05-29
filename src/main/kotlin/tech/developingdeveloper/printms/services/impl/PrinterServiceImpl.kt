package tech.developingdeveloper.printms.services.impl

import org.apache.pdfbox.Loader
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.printing.PDFPageable
import org.apache.pdfbox.printing.PDFPrintable
import org.apache.pdfbox.printing.Scaling
import org.springframework.stereotype.Service
import tech.developingdeveloper.printms.services.PrinterService
import java.awt.print.PrinterJob
import java.io.File
import javax.print.PrintService
import javax.print.PrintServiceLookup
import javax.print.attribute.HashPrintRequestAttributeSet
import javax.print.attribute.standard.Chromaticity
import javax.print.attribute.standard.MediaSizeName

@Service
class PrinterServiceImpl : PrinterService {
    override fun getAllPrinters() {
        val printServices = PrintServiceLookup.lookupPrintServices(null, null)
        println("Number of print services: " + printServices.size)

        println("List of all printers: ")
        for (printer in printServices) println("Printer: " + printer.name)
    }

    override fun getDefaultPrinter() {
        val defaultPrinterService = PrintServiceLookup.lookupDefaultPrintService()
        println("Default Printer: ${defaultPrinterService.name}")
        printAttributes(defaultPrinterService)
    }

    override fun findPrinter(printerName: String): PrintService {
        val printServices = PrintServiceLookup.lookupPrintServices(null, null)
        val printer = printServices.firstOrNull { it.name == printerName }
            ?: throw Exception("Printer with name $printerName not found!")

        println("Printer: ${printer.name}")

        return printer
    }

    override fun printPdf(path: String, printerServiceName: String) {
        val file = File(path)

        if (!file.exists()) throw Exception("File not found at $path")

        val printService: PrintService = findPrinter(printerServiceName)

        val document: PDDocument = Loader.loadPDF(file)

        document.use {
            val job = getPrinterJob(it, printService)
            val attributes = getJobAttributes()
            job.print(attributes)
        }
    }

    private fun getPrinterJob(document: PDDocument, printService: PrintService): PrinterJob {
        val job = PrinterJob.getPrinterJob().apply {
            setPageable(PDFPageable(document))
            setPrintable(PDFPrintable(document, Scaling.ACTUAL_SIZE))
            this.printService = printService
        } ?: throw Exception("Failed to create printer job.")

        return job
    }

    private fun printAttributes(printService: PrintService) {
        val attributes = printService.attributes

        for (a in attributes.toArray()) {
            val name: String = a.name
            val value: String = attributes.get(a.javaClass).toString()
            println("$name(${a.javaClass}) : $value")
        }
    }

    private fun getJobAttributes(): HashPrintRequestAttributeSet {
        val attributes = HashPrintRequestAttributeSet()
        attributes.add(Chromaticity.MONOCHROME)
        attributes.add(MediaSizeName.ISO_A4)
        return attributes
    }
}
