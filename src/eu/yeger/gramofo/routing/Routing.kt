package eu.yeger.gramofo.routing

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.serialization.SerializationException

/**
 * Root routing module of the backend.
 * Includes all routes and defines status pages.
 *
 * @receiver The [Application] the module will be installed in.
 *
 * @author Jan Müller
 */
fun Application.routingModule() = routing {
    route("/") {
        get {
            call.respondText("gramoFO-Backend is available!", contentType = ContentType.Text.Plain)
        }

        modelCheckerRoutes()
    }

    install(StatusPages) {
        exception<Throwable> { cause ->
            call.respond(HttpStatusCode.InternalServerError, cause.message ?: "api.error.unknown")
            throw cause
        }
        exception<SerializationException> { cause ->
            call.respond(HttpStatusCode.BadRequest, cause.message ?: "api.error.unknown")
        }
    }
}
