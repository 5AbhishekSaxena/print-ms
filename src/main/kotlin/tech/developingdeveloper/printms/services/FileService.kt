package tech.developingdeveloper.printms.services

import org.springframework.web.multipart.MultipartFile
import java.io.File


interface FileService {

    fun saveFile(multipartFile: MultipartFile, destination: String?): String
}