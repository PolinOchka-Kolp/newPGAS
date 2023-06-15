package CommonComp

//import react.dom.html.InputType
import csstype.ClassName
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.p
import react.useRef
import react.useState
import web.html.HTMLInputElement
import web.html.InputType

external interface InputCheckboxProps : Props {
    var title: String
    var value: String
    var set: (String) -> Unit
}

val InputCheckbox = FC<InputCheckboxProps> { props ->
    val ref = useRef<HTMLInputElement>()
    var bool by useState(props.value)
    div {
        className = ClassName("col d-flex flex-column justify-content-between align-items-center")

        p {
            className = ClassName("form-check-label")
            +props.title
        }
        input {
            className = ClassName("form-check-input")
            this.ref = ref
            type = InputType.checkbox
            checked = bool == "Да"
            onClick = {
                bool = if (ref.current!!.checked) {
                    props.set("Да")
                    "Да"
                } else {
                    props.set("Нет")
                    "Нет"
                }
            }
        }
    }
}