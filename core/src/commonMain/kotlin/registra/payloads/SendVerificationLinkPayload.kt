package registra.payloads

import kotlinx.serialization.Serializable

@Serializable
data class SendVerificationLinkPayload(
    val url: String,
    val email: String
)