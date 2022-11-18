package registra

import kotlinx.serialization.Serializable

@Serializable
sealed class Status {
//    abstract val on: LocalDate

    @Serializable
    class Submitted : Status()

    @Serializable
    class Verified : Status()

    @Serializable
    class Completed : Status()
}