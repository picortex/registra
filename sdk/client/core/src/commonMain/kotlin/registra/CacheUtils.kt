package registra

import cache.Cache
import registra.params.SignUpParams
import registra.params.VerificationParams

@PublishedApi
internal const val KEY_SIGN_UP_PARAMS = "registra.sign.up.params"

@PublishedApi
internal const val KEY_VERIFICATION_PARAMS = "registra.verification.params"

// --------------------------Sign Up Params --------------------------
inline fun Cache.save(params: SignUpParams) = save(KEY_SIGN_UP_PARAMS, params, SignUpParams.serializer())

inline fun Cache.loadSignUpParams() = load(KEY_SIGN_UP_PARAMS, SignUpParams.serializer())


// -------------------------- Verification Params ---------------------
inline fun Cache.save(params: VerificationParams) = save(KEY_VERIFICATION_PARAMS, params, VerificationParams.serializer())

inline fun Cache.loadVerificationParams() = load(KEY_VERIFICATION_PARAMS, VerificationParams.serializer())

inline fun Cache.removeVerificationParams() = remove(KEY_VERIFICATION_PARAMS)