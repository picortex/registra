package registra

import bitframe.actor.Savable
import bitframe.actor.UNSET
import kotlinx.serialization.Serializable

@Serializable
data class Candidate(
    override val uid: String = UNSET,
    val name: String,
    val email: String,
    val token: String,
    val status: Status = Status.Submitted(),
    override val deleted: Boolean = false
) : Savable {
    override fun copy(uid: String, deleted: Boolean) = Candidate(uid, name, email, token, status, deleted)
}