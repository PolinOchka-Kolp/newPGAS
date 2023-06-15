package components.common

import CommonComp.InputCheckbox
import CommonComp.InputDate
import CommonComp.InputSelect
import CommonComp.InputText
import classes.Reason
import classes.Sphere
import classes.Type
import common.Item
import js.core.jso
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import react.FC
import react.Props
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.li
import tanstack.query.core.QueryKey
import tanstack.react.query.useMutation
import tanstack.react.query.useQueryClient
import tools.HTTPResult
import tools.fetch
import kotlin.js.json
import Config.Config
import csstype.Font
import csstype.FontWeight
import csstype.px
import emotion.react.css
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.ul

external interface CommonProps : Props {
    var sphere: Item<Sphere>
}

val CCommon = FC<CommonProps> { props ->
    val queryClient = useQueryClient()

    val updateMutation = useMutation<HTTPResult, Any, Item<Sphere>, Any>(
        mutationFn = { item ->
            fetch(
                Config.studyPath,
                jso {
                    method = "PUT"
                    headers = json(
                        "Content-Type" to "application/json"
                    )
                    body = Json.encodeToString(item)
                }
            )
        },
        options = jso {
            onSuccess = { _: Any, _: Any, _: Any? ->
                queryClient.invalidateQueries<Any>(arrayOf("sphere").unsafeCast<QueryKey>())
            }
        }
    )

    ReactHTML.div {
        +"Причины:"
        ul {
            props.sphere.elem.reasons.map {
                +it.name
                it.fields.map {
                    div {
                        +"${it.name} = ${it.selected}"
                    }
                }
            }
        }
    }

    ReactHTML.div {
        CCommonItem {
            sphere = props.sphere
            update = { item ->
                updateMutation.mutateAsync(item, null)
            }
        }
    }
}

external interface CommonItem : Props {
    var sphere: Item<Sphere>
    var update: (Item<Sphere>) -> Unit
}

val CCommonItem = FC<CommonItem> { props ->
    ReactHTML.h3 {
        css {
            fontWeight = FontWeight.bold
        }
        +props.sphere.elem.name
    }

    ReactHTML.ul {
        props.sphere.elem.reasons.map {
            li {
                css {
                    paddingLeft = 10.px
                }
                +it.name
                it.fields.map { field ->
                    when (field.type) {
                        Type.Date -> {
                            InputDate {
                                title = field.name
                                text = field.selected
                                set = { new ->
                                    val newValue = field.copy(
                                        selected = new
                                    )
                                    val newList = it.fields.map {
                                        if (it.name == field.name) {
                                            newValue
                                        } else {
                                            it
                                        }
                                    }
                                    val newItem = props.sphere.elem.reasons.map { reason ->
                                        if (it == reason)
                                            Reason(reason.name, newList)
                                        else
                                            reason
                                    }
                                    props.update(Item(props.sphere.elem.copy(reasons = newItem), props.sphere.id))
                                }
                            }
                        }

                        Type.CheckBox -> {
                            InputCheckbox {
                                title = field.name
                                value = field.selected
                                set = { new ->
                                    val newValue = field.copy(
                                        selected = new
                                    )
                                    val newList = it.fields.map {
                                        if (it.name == field.name) {
                                            newValue
                                        } else {
                                            it
                                        }
                                    }
                                    val newItem = props.sphere.elem.reasons.map { reason ->
                                        if (it == reason)
                                            Reason(reason.name, newList)
                                        else
                                            reason
                                    }

                                    props.update(Item(props.sphere.elem.copy(reasons = newItem), props.sphere.id))
                                }
                            }

                        }

                        Type.WriteIn -> {
                            InputText {
                                title = field.name
                                text = field.selected
                                set = { new ->
                                    val newValue = field.copy(
                                        selected = new
                                    )
                                    val newList = it.fields.map {
                                        if (it.name == field.name) {
                                            newValue
                                        } else {
                                            it
                                        }
                                    }
                                    val newItem = props.sphere.elem.reasons.map { reason ->
                                        if (it == reason)
                                            Reason(reason.name, newList)
                                        else
                                            reason
                                    }

                                    props.update(Item(props.sphere.elem.copy(reasons = newItem), props.sphere.id))
                                }
                            }
                        }

                        Type.Select -> {
                            InputSelect {
                                title = field.name
                                values = field.values.toTypedArray()
                                text = field.selected
                                set = { new ->
                                    val newValue = field.copy(
                                        selected = new
                                    )
                                    val newList = it.fields.map {
                                        if (it.name == field.name) {
                                            newValue
                                        } else {
                                            it
                                        }
                                    }
                                    val newItem = props.sphere.elem.reasons.map { reason ->
                                        if (it == reason)
                                            Reason(reason.name, newList)
                                        else
                                            reason
                                    }

                                    props.update(Item(props.sphere.elem.copy(reasons = newItem), props.sphere.id))
                                }
                            }
                        }

                        else -> {}
                    }

                }
            }
            ReactHTML.button {
                +"✚"
                onClick = { click ->
                    val newReason = it.copy(fields = it.fields.map { it.copy(selected = "") })
                    props.update(
                        Item(
                            props.sphere.elem.copy(reasons = props.sphere.elem.reasons + newReason),
                            props.sphere.id
                        )
                    )
                }
            }
        }
    }

}