package command

import classes.Sphere
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CreateZip(
    @SerialName("ФИО") val name: String,
    @SerialName("Группа") val group: String,
    @SerialName("Номер телефона") val telephone: String,
    @SerialName("Причины подачи") val reasons: Map<String, List<String>>
)


