package classes

import kotlinx.serialization.Serializable

@Serializable
data class Sphere(
    val name: String,
    val reasons: List<Reason>
)