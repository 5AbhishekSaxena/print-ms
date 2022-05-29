package tech.developingdeveloper.printms

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import tech.developingdeveloper.printms.services.impl.PrinterServiceImpl

@SpringBootApplication
class PrintMsApplication {

    @Bean
    fun commandLineRunner(): CommandLineRunner {
        return CommandLineRunner {
            val printerService = PrinterServiceImpl()
            printerService.getAllPrinters()
            println()
            printerService.getDefaultPrinter()
            println()
            printerService.findPrinter("HP DJ 2130 series")
            println()
            printerService.printPdf("D:/sample.pdf", "HP DJ 2130 series")
        }
    }
}

fun main(args: Array<String>) {
    runApplication<PrintMsApplication>(*args)
}
