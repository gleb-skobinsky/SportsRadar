package org.sportsradar.sportsradarapp.common.presentation.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun Dp.VerticalSpacer() = Spacer(Modifier.height(this))

@Composable
fun Dp.HorizontalSpacer() = Spacer(Modifier.width(this))

@Composable
fun ColumnScope.FullHeightSpacer() = Spacer(Modifier.weight(1f))