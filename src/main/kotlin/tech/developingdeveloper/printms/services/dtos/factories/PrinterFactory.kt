package tech.developingdeveloper.printms.services.dtos.factories

import org.springframework.stereotype.Service
import tech.developingdeveloper.printms.entity.Printer
import tech.developingdeveloper.printms.entity.enum.PrinterJobAcceptanceStatus
import tech.developingdeveloper.printms.entity.enum.PrinterActivityStatus
import javax.print.attribute.standard.PrinterIsAcceptingJobs

@Service
class PrinterFactory {
    fun createPrinter(
        name: String,
        activityStatus: PrinterActivityStatus,
        printerIsAcceptingJobsAttribute: PrinterIsAcceptingJobs,
        attributes: Map<String, String>
    ): Printer {
        val isAcceptingJobs = getIsAcceptingJobs(printerIsAcceptingJobsAttribute)
        return Printer(name, isAcceptingJobs, activityStatus, attributes)
    }

    private fun getIsAcceptingJobs(printerIsAcceptingJobs: PrinterIsAcceptingJobs): PrinterJobAcceptanceStatus {
        val isAcceptingJobs = printerIsAcceptingJobs.value == PrinterIsAcceptingJobs.ACCEPTING_JOBS.value
        return if (isAcceptingJobs)
            PrinterJobAcceptanceStatus.ACCEPTING
        else
            PrinterJobAcceptanceStatus.NOT_ACCEPTING
    }
}