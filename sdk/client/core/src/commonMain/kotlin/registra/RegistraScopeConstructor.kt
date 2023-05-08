package registra

import registra.internal.RegistraScopeImpl

inline fun RegistraScope(config: RegistraScopeConfig<RegistraApi>): RegistraScope = RegistraScopeImpl(config)