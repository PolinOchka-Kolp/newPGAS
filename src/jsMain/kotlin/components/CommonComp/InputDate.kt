package CommonComp

//import react.dom.html.InputType

import csstype.ClassName
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.p
import react.useState
import web.html.InputType
import kotlin.js.Date

external interface InputDateProps : Props {
    var title: String
    var set: (String) -> Unit
    var text: String
}

val InputDate = FC<InputDateProps> { props ->
    var text by useState(props.text)
    div {
        className = ClassName("col-2 d-flex flex-column justify-content-between align-items-center")

        p {
            +props.title
        }
        input {
            defaultValue = text
            this.className = ClassName("form-select")
            type = InputType.date
            onChange = {
                props.set(it.target.value)
                text = it.target.value
            }
            min = minDate()
            max = date()
        }
    }
}

fun date(): String {
    val currentDate = Date()
    val year = currentDate.getFullYear()
    var month = (currentDate.getMonth() + 1).toString()
    if (month.length < 2)
        month = "0$month"
    var day = currentDate.getDate().toString()
    if (day.length < 2)
        day = "0$day"
    return "$year-$month-$day"
}

fun minDate(): String {
    val currentDate = Date()
    val year = currentDate.getFullYear() - 1
    var month = (currentDate.getMonth() + 1).toString()
    if (month.length < 2)
        month = "0$month"
    var day = currentDate.getDate().toString()
    if (day.length < 2)
        day = "0$day"
    return "$year-$month-$day"
}