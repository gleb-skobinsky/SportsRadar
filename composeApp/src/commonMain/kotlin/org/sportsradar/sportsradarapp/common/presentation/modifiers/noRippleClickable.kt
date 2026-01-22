package org.sportsradar.sportsradarapp.common.presentation.modifiers

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.Modifier

private val source = MutableInteractionSource()

fun Modifier.noRippleClickable(
    enabled: Boolean = true,
    onClick: () -> Unit
) = this
    .clickable(
        interactionSource = source,
        indication = null,
        enabled = enabled,
        onClick = onClick
    )