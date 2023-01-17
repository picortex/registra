package registra

import registra.internal.RegistraScopeImpl
import viewmodel.ScopeConfig

inline fun RegistraScope(config: ScopeConfig<RegistraApi>): RegistraScope = RegistraScopeImpl(config)