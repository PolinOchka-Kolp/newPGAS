package componensts

import Config.Config
import classes.Sphere
import command.CreateZip
import common.Item
import components.menu.CDownload
import components.common.CCommon
import csstype.FontWeight
import csstype.TextAlign
import emotion.react.css
import js.core.get
import js.core.jso
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import queryError.QueryError
import react.FC
import react.Props
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.form
import tanstack.query.core.QueryKey
import tanstack.react.query.useMutation
import tanstack.react.query.useQuery
import tanstack.react.query.useQueryClient
import tools.HTTPResult
import tools.fetch
import tools.fetchText
import web.http.FormData
import kotlin.js.json

val CMenu = FC<Props> {
    val query = useQuery<String, QueryError, String, QueryKey>(
        queryKey = arrayOf("sphere").unsafeCast<QueryKey>(),
        queryFn = {
            fetchText(Config.studyPath)
        })
    val queryClient = useQueryClient()

    val updateMutation = useMutation<HTTPResult, Any, FormData, Any>(
        mutationFn = { item ->
            fetch(
                Config.filePath + "addZip",
                jso {
                    method = "POST"
//                    headers = json(
//                        "Content-Type" to "application/json"
//                    )
                    body = item
                }
            )
        },
        options = jso {
            onSuccess = { _: Any, _: Any, _: Any? ->
                queryClient.invalidateQueries<Any>(arrayOf("sphere").unsafeCast<QueryKey>())
            }
        }
    )

    if (query.isSuccess) {
        val data = Json.decodeFromString<List<Item<Sphere>>>(query.data ?: "")

        div {
            div {
                ReactHTML.p {
                    css {
                        textAlign = TextAlign.center
                        fontWeight = FontWeight.bold
                    }
                    +"Заявление"
                }
                ReactHTML.p {
                    css {
                        textAlign = TextAlign.center
                    }
                    +Config.greating
                }
            }
            CDownload {
                onSave = { Name, Group, Number, Photos ->
                    val reasons = data.map {it.elem}.map {
                        it.reasons.map {
                            it.name to it.fields.map { it.selected }
                        }
                    }.flatten().toMap()

                    val command = CreateZip(
                        Name,
                        Group,
                        Number,
                        reasons
                    )

                    val formData = FormData()

                    formData.append("command", Json.encodeToString(command))

                    for (i in 0..(Photos?.length ?: 0)) {
                        val photo = Photos?.get(i)
                        if (photo != null)
                            formData.append("photo$i", photo)
                    }
                    updateMutation.mutateAsync(formData, null)
                }
            }

            data.map {
                CCommon {
                    sphere = it
                }
            }
        }
    }
}





