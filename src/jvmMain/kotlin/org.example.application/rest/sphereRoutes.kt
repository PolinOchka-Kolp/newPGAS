package org.example.application.rest

import Config.Config
import io.ktor.server.routing.*
import org.example.application.repo.listSphere
import org.example.application.repo.repoRoutes

fun Route.sphere() {
    route(Config.studyPath) {
        repoRoutes(listSphere)
    }
}