package components.reader

import csstype.NamedColor
import csstype.None
import csstype.px
import emotion.react.css
import react.FC
import react.Props
import react.dom.html.ButtonType
import react.dom.html.FormMethod
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import web.html.InputType
import Config.Config

val reader = FC<Props> {
    div {
        ReactHTML.form {
            name = "file"
            encType = "multipart/form-data"
            method = FormMethod.post
            action = Config.filePath
            div {
                input {
                    name = "file"
                    type = InputType.file
                    accept = ".txt"
                }
                ReactHTML.button {
                    css {
                        background = NamedColor.white
                        border = None.none
                        fontSize = 30.px
                        hover {
                            background = NamedColor.whitesmoke
                        }
                    }
                    type = ButtonType.submit
                    +"☑"
                }

            }
        }
    }
}