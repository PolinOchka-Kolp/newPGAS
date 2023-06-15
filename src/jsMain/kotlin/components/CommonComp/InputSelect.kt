package CommonComp

//import react.dom.html.InputType
import csstype.ClassName
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.option
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.select
import react.useRef
import web.html.HTMLSelectElement

external interface InputSelectProps : Props {
    var title: String
    var values: Array<String>
    var set: (String) -> Unit
    var text: String
}

val InputSelect = FC<InputSelectProps> { props ->
    val ref = useRef<HTMLSelectElement>()
    div {
        className = ClassName("col d-flex flex-column justify-content-between align-items-center")

        p {
            +props.title
        }
        select {
            this.ref = ref
            this.className = ClassName("form-select")
            option {
                disabled = true
                selected = true
                +"-- Выберите пункт --"
            }
            for (value in props.values) {
                option {
                    if (props.text == value){
                        selected = true
                    }
                    +value
                }
            }
            onChange = {
                props.set(ref.current!!.value)
            }
        }
    }
}