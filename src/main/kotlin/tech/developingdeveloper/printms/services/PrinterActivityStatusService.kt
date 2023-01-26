package tech.developingdeveloper.printms.services

import tech.developingdeveloper.printms.entity.enum.PrinterActivityStatus


interface PrinterActivityStatusService {

    fun getPrinterActivityStatus(): Map<String, PrinterActivityStatus>
    fun getPrinterActivityStatus(printerName: String): PrinterActivityStatus
}