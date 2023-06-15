package org.example.application.rest

import Config.Config
import classes.Sphere
import command.CreateZip
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.example.application.repo.listSphere
import java.awt.Desktop
import java.io.*
import java.util.*
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

fun Route.file() {
    route(Config.filePath) {
        post {
            val multiPartData = call.receiveMultipart()
            var fileName = ""
            val time = Date().time.toString()
            multiPartData.forEachPart { part ->
                when (part) {
                    is PartData.FileItem -> {
                        fileName = part.originalFileName as String
                        val fileBytes = part.streamProvider().readBytes()
                        File("conf/$time.txt").writeBytes(fileBytes)
                    }

                    else -> {}
                }
                part.dispose()
            }
            if (fileName.endsWith(".txt")) {
                val file1 = File("conf/$time.txt")
                val obj = Json.decodeFromString<List<Sphere>>(file1.readText())
                obj.map {
                    listSphere.create(it)
                }
                return@post call.respondRedirect("/#/file/$time")
            }else{
                call.respondRedirect("/#/")
            }
        }
        post("addZip") {
            val multipart = call.receiveMultipart()
            var zipOut: ZipOutputStream? = null

            multipart.forEachPart { part ->
                if (part is PartData.FormItem && part.name == "command") {
                    val data = Json.decodeFromString<CreateZip>(part.value)
                    zipOut = ZipOutputStream(FileOutputStream("C:/Users/Public/Documents/pgas.zip"))
                    val fileName = File("Заявление.txt")
                    val fileText = File("Перенесите все необходимые документы в папку.txt")
                    fileText.createNewFile()

                    fileName.writeText(Json.encodeToString(data))

                    val files = arrayOf(fileName, fileText)

                    for (file in files) {
                        val entry = ZipEntry(file.name)
                        zipOut!!.putNextEntry(entry)

                        val input = FileInputStream(file)
                        input.copyTo(zipOut!!)

                        zipOut!!.closeEntry()
                        input.close()
                    }
                }
                if (part is PartData.FileItem) {
                    val fileStream: InputStream = part.streamProvider()

                    val entry = ZipEntry(part.originalFileName!!)

                    zipOut!!.putNextEntry(entry)

                    fileStream.copyTo(zipOut!!)

                    zipOut!!.closeEntry()
                    fileStream.close()
                }
                part.dispose()
            }
//
//            // Закрываем архив
            zipOut!!.close()
            val desk = Desktop.getDesktop()
            desk.open(File("C:/Users/Public/Documents/pgas.zip"))
        }
    }
}