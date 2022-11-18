package registra

// TODO: Consider renaming this interface
//  coz it is being used in the server side (as a service config)
//  and its being used in the client side (as an api config)
interface SignUpApiConfig {
    val verificationUrl: String
}