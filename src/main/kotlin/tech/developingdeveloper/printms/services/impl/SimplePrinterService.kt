package tech.developingdeveloper.printms.services.impl

import org.apache.pdfbox.Loader
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.printing.PDFPageable
import org.apache.pdfbox.printing.PDFPrintable
import org.apache.pdfbox.printing.Scaling
import org.springframework.stereotype.Service
import tech.developingdeveloper.printms.entity.Printer
import tech.developingdeveloper.printms.services.PrinterService
import tech.developingdeveloper.printms.services.dtos.factories.PrinterFactory
import java.awt.print.PrinterJob
import java.io.File
import java.util.*
import javax.print.PrintService
import javax.print.PrintServiceLookup
import javax.print.attribute.HashPrintRequestAttributeSet
import javax.print.attribute.standard.Chromaticity
import javax.print.attribute.standard.JobName
import javax.print.attribute.standard.MediaSizeName
import javax.print.attribute.standard.PrinterIsAcceptingJobs

@Service
class SimplePrinterService(
    private val printerFactory: PrinterFactory
) : PrinterService {
    override fun getAllPrinters(): List<Printer> {
        val printServices = PrintServiceLookup.lookupPrintServices(null, null)
        return printServices.map {
            printerFactory.createPrinter(
                name = it.name,
                printerIsAcceptingJobsAttribute = it.getPrinterIsAcceptingJobsAttribute(),
                attributes = getAttributes(it)
            )
        }
    }

    override fun getDefaultPrinter(): Printer {
        val defaultPrinterService = PrintServiceLookup.lookupDefaultPrintService()
            ?: throw Exception("No default printer found.")

        return printerFactory.createPrinter(
            name = defaultPrinterService.name,
            printerIsAcceptingJobsAttribute = defaultPrinterService.getPrinterIsAcceptingJobsAttribute(),
            attributes = getAttributes(defaultPrinterService)
        )
    }

    override fun getPrinter(printerName: String): Printer {
        if (printerName == "default") return getDefaultPrinter()

        val printerService = findPrinter(printerName)
        return printerFactory.createPrinter(
            name = printerService.name,
            printerIsAcceptingJobsAttribute = printerService.getPrinterIsAcceptingJobsAttribute(),
            attributes = getAttributes(printerService)
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
            job.print(attributes)
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

    private fun getAttributes(printService: PrintService): Map<String, String> {
        val printServiceAttributeSet = printService.attributes

        val attributes = mutableMapOf<String, String>()

        for (a in printServiceAttributeSet.toArray()) {
            val name: String = a.name
            val value: String = printServiceAttributeSet.get(a.javaClass).toString()
            println("$name(${a.javaClass}) : $value")
            attributes[name] = value
        }
        return attributes
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
