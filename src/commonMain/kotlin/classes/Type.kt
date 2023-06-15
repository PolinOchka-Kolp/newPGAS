package classes

import kotlinx.serialization.Serializable

@Serializable
enum class Type {
    Date, Select, CheckBox, WriteIn
}