package tech.developingdeveloper.printms.services.dtos.factories

import org.springframework.stereotype.Service
import tech.developingdeveloper.printms.services.dtos.PrinterDTO
import javax.print.attribute.standard.PrinterIsAcceptingJobs

@Service
class PrinterDTOFactory {
    fun createPrinter(
        name: String,
        printerIsAcceptingJobsAttribute: PrinterIsAcceptingJobs,
        attributes: Map<String, String>
    ): PrinterDTO {
        val isAcceptingJobs = getIsAcceptingJobs(printerIsAcceptingJobsAttribute)
        return PrinterDTO(name, isAcceptingJobs, attributes)
    }

    private fun getIsAcceptingJobs(printerIsAcceptingJobs: PrinterIsAcceptingJobs): PrinterDTO.PrinterIsAcceptingJobs {
        val isAcceptingJobs = printerIsAcceptingJobs.value == PrinterIsAcceptingJobs.ACCEPTING_JOBS.value
        return if (isAcceptingJobs)
            PrinterDTO.PrinterIsAcceptingJobs.ACCEPTING_JOBS
        else
            PrinterDTO.PrinterIsAcceptingJobs.NOT_ACCEPTING_JOBS
    }
}