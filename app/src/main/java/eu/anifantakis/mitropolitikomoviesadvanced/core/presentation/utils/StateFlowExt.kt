package eu.anifantakis.mitropolitikomoviesadvanced.core.presentation.utils

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Converts a [StateFlow] to a [State] that can be used with Jetpack Compose.
 */
fun <T> StateFlow<T>.toComposeState(scope: CoroutineScope): State<T> {
    val composeState = mutableStateOf(value)
    scope.launch {
        this@toComposeState.collect { newValue ->
            composeState.value = newValue
        }
    }
    return composeState
}