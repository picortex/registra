package registra

import registra.internal.RegistraScopeImpl
import viewmodel.ScopeConfig

inline fun RegistraScope(config: RegistraScopeConfig<RegistraApi>): RegistraScope = RegistraScopeImpl(config)