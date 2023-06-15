package classes

import kotlinx.serialization.Serializable

@Serializable
data class Reason(
    val name: String,
    val fields: List<Field>
)