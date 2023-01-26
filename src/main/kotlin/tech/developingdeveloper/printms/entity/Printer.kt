package tech.developingdeveloper.printms.entity

import tech.developingdeveloper.printms.entity.enum.PrinterJobAcceptanceStatus
import tech.developingdeveloper.printms.entity.enum.PrinterActivityStatus


class Printer(
    val name: String,
    val jobAcceptanceStatus: PrinterJobAcceptanceStatus,
    val activityStatus: PrinterActivityStatus,
    val attributes: Map<String, String>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Printer

        if (name != other.name) return false
        if (jobAcceptanceStatus != other.jobAcceptanceStatus) return false
        if (attributes != other.attributes) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + jobAcceptanceStatus.hashCode()
        result = 31 * result + attributes.hashCode()
        return result
    }

    override fun toString(): String {
        return "Printer(name='$name', jobAcceptanceStatus=$jobAcceptanceStatus, activityStatus=$activityStatus, attributes=$attributes)"
    }

}