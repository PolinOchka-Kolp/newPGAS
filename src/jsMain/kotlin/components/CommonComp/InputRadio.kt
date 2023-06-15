package CommonComp

//import react.dom.html.InputType
import csstype.ClassName
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.p
import react.useRef
import web.html.HTMLInputElement
import web.html.InputType

external interface InputRadioProps : Props {
    var title: String
    var value: String
    var name: String
    var currentValue: String
    var set: (String) -> Unit
}

val InputRadio = FC<InputRadioProps> { props ->
    val ref = useRef<HTMLInputElement>()
    div {
        className = ClassName("col d-flex flex-column justify-content-between align-items-center")

        p {
            className = ClassName("form-check-label")
            +props.title
        }
        input {
            className = ClassName("form-check-input")
            this.ref = ref
            type = InputType.radio
            name = props.name
            value = props.value
            if (props.currentValue == value){
                checked = true
            }
            onClick = {
                props.set(ref.current!!.value)
            }
        }
    }
}