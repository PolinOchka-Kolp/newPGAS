package classes

import kotlinx.serialization.Serializable

@Serializable
data class Field(
    val name: String,
    val type: Type,
    val selected: String = "",
    val values: List<String> = emptyList()
)