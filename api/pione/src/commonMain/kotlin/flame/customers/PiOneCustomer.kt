package flame.customers

import kotlinx.serialization.Serializable

@Serializable
data class PiOneCustomer(
    val id: Int?,
    val name: String,
    val number: String?,
    val idNumber: String?,
    val customerType: String?,
    val dateOfBirth: PiOneDate?,
    val industry: String?,
    val website: String?,
    val pointOfContact: String?,
    val telephone: String?,
    val email: String?,
    val country: String?,
    val mobile: String?,
    val address: String?,
    val vatNumber: String?,
    val registrationNumber: String?,
    val locationData: PiOneLocation?,
    val location: String?,
)
