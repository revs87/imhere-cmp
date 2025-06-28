package com.rvcoding.imhere.data.local.ksafe

import androidx.compose.runtime.SnapshotMutationPolicy
import eu.eu.anifantakis.lib.ksafe.compose.KSafeComposeState
import kotlin.properties.ReadWriteProperty

actual inline fun <reified T> KSafeWrapper.KSafeComposeStateWrapper(
    initialValue: T,
    noinline valueSaver: (T) -> Unit,
    policy: SnapshotMutationPolicy<T>
): ReadWriteProperty<Any?, T> = KSafeComposeState(initialValue, valueSaver, policy)
