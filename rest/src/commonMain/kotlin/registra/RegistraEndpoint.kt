package registra

class RegistraEndpoint(val root: String) {
    val base = "$root/registration"
    val signUp = "$base/sign-up"
    val verify = "$base/verify"
    val sendVerificationLink = "$base/send-verification-link"
}