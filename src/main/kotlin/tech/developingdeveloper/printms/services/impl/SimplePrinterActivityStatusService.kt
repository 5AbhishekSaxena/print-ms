package tech.developingdeveloper.printms.services.impl

import com.profesorfalken.wmi4java.WMI4Java
import com.profesorfalken.wmi4java.WMIClass
import com.profesorfalken.wmi4java.WMIException
import org.springframework.stereotype.Service
import tech.developingdeveloper.printms.entity.enum.PrinterActivityStatus
import tech.developingdeveloper.printms.services.PrinterActivityStatusService

@Service
class SimplePrinterActivityStatusService : PrinterActivityStatusService {

    private val wmi: WMI4Java by lazy { WMI4Java.get() }
    private val wmiProperties = listOf(WMI_PRINTER_NAME_KEY, WMI_WORK_OFFLINE_KEY)

    override fun getPrinterActivityStatus(): Map<String, PrinterActivityStatus> {
        return wmi
            .properties(wmiProperties)
            .getWMIObjectList(WMIClass.WIN32_PRINTER)
            .asSequence()
            .filterNotNull()
            .filterPrinterResults()
            .transformResult()
    }

    override fun getPrinterActivityStatus(printerName: String): PrinterActivityStatus {
        try {
            val filter = listOf("\$_.$WMI_PRINTER_NAME_KEY -eq \"$printerName\"")
            val result = wmi
                .properties(wmiProperties)
                .filters(filter)
                .getWMIObjectList(WMIClass.WIN32_PRINTER)
                .asSequence()
                .filterNotNull()
                .filterPrinterResults()
                .transformResult()

            return result[printerName] ?: PrinterActivityStatus.OFFLINE
        } catch (exception: WMIException) {
            val message = "Failed to getActivityStatus for $printerName"
            throw Exception(message)
        }
    }

    private fun Sequence<Map<String, String>>.filterPrinterResults(): Sequence<Map<String, String>> {
        return filter { it.containsKey(WMI_PRINTER_NAME_KEY) && it.containsKey(WMI_WORK_OFFLINE_KEY) }
    }

    private fun Sequence<Map<String, String>>.transformResult(): Map<String, PrinterActivityStatus> {
        return associateBy(
            keySelector = { it.getValue(WMI_PRINTER_NAME_KEY) },
            valueTransform = { if (it[WMI_WORK_OFFLINE_KEY] == "False") PrinterActivityStatus.ONLINE else PrinterActivityStatus.OFFLINE }
        ).toMutableMap()
    }

    companion object {
        private const val WMI_PRINTER_NAME_KEY = "Name"
        private const val WMI_WORK_OFFLINE_KEY = "WorkOffline"
    }
}