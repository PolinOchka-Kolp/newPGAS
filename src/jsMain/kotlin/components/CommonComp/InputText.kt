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

external interface InputTextProps : Props {
    var set: (String) -> Unit
    var text: String
    var title: String
}

val InputText = FC<InputTextProps> { props ->
    var text by useState(props.text)
    div {
        className = ClassName("col-4 d-flex flex-column justify-content-between align-items-center")

        p {
            +props.title
        }
        input {
            className = ClassName("form-control")
            defaultValue = text
            type = InputType.text
            onChange = {
                props.set(it.target.value)
                text = it.target.value
            }
        }
    }
}