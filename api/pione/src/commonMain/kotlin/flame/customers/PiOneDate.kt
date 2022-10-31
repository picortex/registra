package flame.customers

import kotlinx.serialization.Serializable

@Serializable
data class PiOneDate(
    val time: Long,
    val formatted: String? = null
)
