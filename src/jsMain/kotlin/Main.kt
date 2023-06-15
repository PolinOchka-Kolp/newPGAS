import componensts.CMenu

import components.reader.reader
import react.FC
import react.Props
import react.create
import react.dom.client.createRoot
import react.router.Route
import react.router.Routes
import react.router.dom.HashRouter
import tanstack.query.core.QueryClient
import tanstack.react.query.QueryClientProvider
import web.dom.document
import kotlin.js.Date.Companion.now


fun main() {
    val container = document.getElementById("root")!!
    createRoot(container).render(App.create())
}

val App = FC<Props> {
    HashRouter {
        QueryClientProvider {
            client = QueryClient()

            Routes {
                Route {
                    path = "/"
                    element = reader.create()
                }
                Route {
                    path = "file/:fileName"
                    element = CMenu.create {
                        key = now().toString()
                    }
                }
            }
        }
    }
}