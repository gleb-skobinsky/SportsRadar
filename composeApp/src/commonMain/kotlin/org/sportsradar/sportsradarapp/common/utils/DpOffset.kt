package org.sportsradar.sportsradarapp.common.utils

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.DpOffset

fun Density.toOffset(dpOffset: DpOffset) = Offset(dpOffset.x.toPx(), dpOffset.y.toPx())