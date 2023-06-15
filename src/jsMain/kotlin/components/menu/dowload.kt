package components.menu

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import react.FC
import react.Props
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.label
import react.useState
import web.file.FileList
import web.html.InputType

external interface DownloadProps : Props {
    //var spheres: List<Sphere>
    var onSave: (Name, Group, Number, FileList?) -> Unit
}

typealias Name = String
typealias Number = String
typealias Group = String
typealias Photos = String

val CDownload = FC<DownloadProps> { props ->
    var inputName by useState("")
    var group by useState("")
    var telephone by useState("")
    var photos: FileList? by useState()

    div {
        label {
            +"ФИО"
            form = "fullName"
        }
        input {
            name = "fullName"
            type = InputType.text
            value = inputName
            onChange = { inputName = it.target.value }
        }
        label {
            +"Группа"
            form = "group"
        }
        input {
            name = "group"
            type = InputType.text
            value = group
            onChange = { group = it.target.value }
        }
        label {
            +"Номер телефона"
            form = "telephone"
        }
        input {
            name = "telephone"
            type = InputType.tel
            value = telephone
            onChange = { telephone = it.target.value }
        }
        label {
            +"Фотографии"
            form = "photos"
        }
        input {
            multiple = true
            name = "photos"
            type = InputType.file
            onChange = {
                photos = it.target.files
            }
        }
    }

    div {
        ReactHTML.button {
            +"Сохранить данные о студенте в архив"
            onClick = {
                props.onSave(inputName, group, telephone, photos)
            }
        }
    }
}

