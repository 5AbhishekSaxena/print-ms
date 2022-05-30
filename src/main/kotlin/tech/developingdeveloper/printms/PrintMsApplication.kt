package tech.developingdeveloper.printms

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import tech.developingdeveloper.printms.services.impl.PrinterServiceImpl

@SpringBootApplication
class PrintMsApplication

fun main(args: Array<String>) {
    runApplication<PrintMsApplication>(*args)
}
