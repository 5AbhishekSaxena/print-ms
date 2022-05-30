package tech.developingdeveloper.printms.services.impl

import org.apache.pdfbox.Loader
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.printing.PDFPageable
import org.apache.pdfbox.printing.PDFPrintable
import org.apache.pdfbox.printing.Scaling
import org.springframework.stereotype.Service
import tech.developingdeveloper.printms.services.PrinterService
import tech.developingdeveloper.printms.services.dtos.PrinterDTO
import tech.developingdeveloper.printms.services.dtos.factories.PrinterDTOFactory
import java.awt.print.PrinterJob
import java.io.File
import java.net.URI
import java.util.*
import javax.print.PrintService
import javax.print.PrintServiceLookup
import javax.print.attribute.HashAttributeSet
import javax.print.attribute.HashPrintRequestAttributeSet
import javax.print.attribute.standard.Chromaticity
import javax.print.attribute.standard.JobName
import javax.print.attribute.standard.MediaSizeName
import javax.print.attribute.standard.PrinterIsAcceptingJobs
import javax.print.attribute.standard.PrinterState

@Service
class PrinterServiceImpl(
    private val printerDTOFactory: PrinterDTOFactory
) : PrinterService {
    override fun getAllPrinters(): List<PrinterDTO> {
        val printServices = PrintServiceLookup.lookupPrintServices(null, null)
        return printServices.map {
            printerDTOFactory.createPrinter(
                name = it.name,
                printerIsAcceptingJobsAttribute = it.getPrinterIsAcceptingJobsAttribute()
            )
        }
    }

    override fun getDefaultPrinter(): PrinterDTO {
        val defaultPrinterService = PrintServiceLookup.lookupDefaultPrintService()
            ?: throw Exception("No default printer found.")

        return printerDTOFactory.createPrinter(
            name = defaultPrinterService.name,
            printerIsAcceptingJobsAttribute = defaultPrinterService.getPrinterIsAcceptingJobsAttribute()
        )
    }

    override fun getPrinter(printerName: String): PrinterDTO {
        val printerService = findPrinter(printerName)
        return printerDTOFactory.createPrinter(
            name = printerService.name,
            printerIsAcceptingJobsAttribute = printerService.getPrinterIsAcceptingJobsAttribute()
        )
    }

    override fun printPdf(path: String, printerServiceName: String) {
        val file = File(path)

        if (!file.exists()) throw Exception("File not found at $path")

        val printService: PrintService = findPrinter(printerServiceName)

        val document: PDDocument = Loader.loadPDF(file)

        document.use {
            val job = getPrinterJob(it, printService)
            val attributes = buildJobAttributes(file.name)
            //job.print(attributes)
        }
    }

    private fun findPrinter(printerName: String): PrintService {
        val printServices = PrintServiceLookup.lookupPrintServices(null, null)

        return printServices.firstOrNull { it.name == printerName }
            ?: throw Exception("Printer with name $printerName not found!")
    }

    private fun getPrinterJob(document: PDDocument, printService: PrintService): PrinterJob {
        val job = PrinterJob.getPrinterJob().apply {
            setPageable(PDFPageable(document))
            setPrintable(PDFPrintable(document, Scaling.SCALE_TO_FIT))
            this.printService = printService
        } ?: throw Exception("Failed to create printer job.")

        return job
    }

    override fun isPrinterPresent(printerName: String): Boolean {
        val printServices = PrintServiceLookup.lookupPrintServices(null, null)

        return printServices.firstOrNull { it.name == printerName } != null
    }

    private fun printAttributes(printService: PrintService) {
        val attributes = printService.attributes

        for (a in attributes.toArray()) {
            val name: String = a.name
            val value: String = attributes.get(a.javaClass).toString()
            println("$name(${a.javaClass}) : $value")
        }
    }

    private fun buildJobAttributes(fileName: String): HashPrintRequestAttributeSet {
        val attributes = HashPrintRequestAttributeSet().apply {
            add(Chromaticity.MONOCHROME)
            add(MediaSizeName.ISO_A4)
            add(JobName(fileName, Locale.getDefault()))
        }
        return attributes
    }
}

private fun PrintService.getPrinterIsAcceptingJobsAttribute() =
    this.getAttribute(PrinterIsAcceptingJobs::class.java)
