package tech.developingdeveloper.printms.services.impl

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import tech.developingdeveloper.printms.services.FileService
import java.io.File
import java.io.FileOutputStream

@Service
class SimpleFileService : FileService {

    override fun saveFile(multipartFile: MultipartFile, destination: String?): String {
        val tempFile = saveFile(multipartFile, destination ?: getDefaultDestination())
        return tempFile.path
    }

    @JvmName("saveFileWithNonNullDestination")
    private fun saveFile(multipartFile: MultipartFile, destination: String): File {
        val originalFileName = multipartFile.originalFilename ?: throw Exception("File name is null")

        val destinationFile = File(destination)

        if (!destinationFile.exists())
            destinationFile.mkdirs()

        val tempFile = File("$destination\\$originalFileName")
        tempFile.createNewFile()
        val fos = FileOutputStream(tempFile)
        fos.use {
            fos.write(multipartFile.bytes)
        }

        return tempFile
    }

    private fun getDefaultDestination(): String {
        val homePath = System.getProperty("user.home")
        return "$homePath\\documents\\printer_documents"
    }
}