package tools

import js.core.asList
import org.w3c.files.Blob
import web.file.FileList
import kotlin.js.Promise

@JsModule("jszip")
@JsNonModule
external class JSZip {
    fun file(name: String, data: dynamic, options: dynamic = definedExternally): JSZip
    fun generateAsync(options: dynamic = definedExternally): Promise<Blob>
}

fun createZip(files: FileList, jsonBlob: Blob): Promise<Blob> {
    val zip = JSZip()
    files.asList().map {
        zip.file(it.name, it)
    }

    zip.file("info.json", jsonBlob)
    return zip.generateAsync(object {
        var type = Blob()
    })
}