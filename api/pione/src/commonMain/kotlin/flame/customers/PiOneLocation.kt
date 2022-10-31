package flame.customers

import kotlinx.serialization.Serializable

@Serializable
data class PiOneLocation(
    val latitude: Double? = null,
    val longitude: Double? = null,
    val address: String? = null
)
