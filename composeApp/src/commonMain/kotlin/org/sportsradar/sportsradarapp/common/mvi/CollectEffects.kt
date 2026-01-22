package org.sportsradar.sportsradarapp.common.mvi

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.collectLatest

@Composable
fun <Effect : BaseEffect> BaseViewModel<*, *, Effect>.CollectEffects(onEffect: suspend (Effect) -> Unit) {
    LaunchedEffect(Unit) {
        this@CollectEffects.effect.collectLatest {
            onEffect(it)
        }
    }
}