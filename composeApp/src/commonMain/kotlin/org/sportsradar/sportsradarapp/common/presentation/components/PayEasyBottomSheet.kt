package org.sportsradar.sportsradarapp.common.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import org.sportsradar.uiKit.theme.LocalSportsRadarTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import org.sportsradar.sportsradarapp.common.presentation.LocalScreenSize
import org.sportsradar.sportsradarapp.common.presentation.modifiers.noRippleClickable
import org.sportsradar.sportsradarapp.common.utils.platform

@Stable
class SportsRadarAppBottomSheetState(initialState: Boolean) {
    var shown by mutableStateOf(initialState)
        private set

    fun expand() {
        shown = true
    }

    fun hide() {
        shown = false
    }

    companion object {
        val Saver = listSaver(
            save = { listOf(it.shown) },
            restore = {
                SportsRadarAppBottomSheetState(it[0])
            }
        )
    }
}

@Composable
fun rememberSportsRadarAppBottomSheetState(initialState: Boolean = false): SportsRadarAppBottomSheetState {
    return rememberSaveable(
        saver = SportsRadarAppBottomSheetState.Saver
    ) {
        SportsRadarAppBottomSheetState(initialState)
    }
}

@Composable
fun SportsRadarAppBottomSheetScaffold(
    sheetState: SportsRadarAppBottomSheetState,
    modifier: Modifier = Modifier,
    sheetHeightFraction: Float = .9f,
    bottomSheetShape: Shape = RoundedCornerShape(20.dp, 20.dp),
    hideBottomSheetOnClickOutside: Boolean = false,
    sheetPadding: PaddingValues = PaddingValues.Absolute(16.dp, 0.dp, 16.dp, 16.dp),
    bottomSheetColor: Color = LocalSportsRadarTheme.colors.surfaceVariant,
    bottomSheetContent: @Composable ColumnScope.() -> Unit,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    content: @Composable (top: Dp, bottom: Dp) -> Unit
) {
    SportsRadarAppScaffold(
        topBar = topBar,
        modifier = modifier,
        bottomBar = bottomBar,
        content = content
    )
    SportsRadarAppBottomSheet(
        sheetState,
        sheetHeightFraction,
        hideBottomSheetOnClickOutside,
        bottomSheetShape,
        bottomSheetColor,
        sheetPadding,
        bottomSheetContent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SportsRadarAppBottomSheet(
    sheetState: SportsRadarAppBottomSheetState,
    sheetHeightFraction: Float = .9f,
    hideBottomSheetOnClickOutside: Boolean = false,
    bottomSheetShape: Shape = RoundedCornerShape(20.dp, 20.dp),
    bottomSheetColor: Color = LocalSportsRadarTheme.colors.surfaceVariant,
    sheetPadding: PaddingValues = PaddingValues.Absolute(16.dp, 0.dp, 16.dp, 16.dp),
    bottomSheetContent: @Composable (ColumnScope.() -> Unit)
) {
    var completelyHidden by rememberSaveable { mutableStateOf(!sheetState.shown) }
    val screenHeightRatio by animateFloatAsState(
        if (sheetState.shown) sheetHeightFraction else 0f,
        label = "Bottom sheet height animation",
        animationSpec = tween(300),
        finishedListener = { finalValue ->
            if (finalValue == 0f) {
                completelyHidden = true
            }
        }
    )
    val imeBottom = WindowInsets.ime.getBottom(LocalDensity.current)
    val screenHeight = LocalScreenSize.current.height
    val animatedSheetFraction = screenHeight - (screenHeight * screenHeightRatio)
    LaunchedEffect(sheetState.shown) {
        if (sheetState.shown) {
            completelyHidden = false
        }
    }
    if (!completelyHidden) {
        Dialog(
            onDismissRequest = {},
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                dismissOnClickOutside = false
            )
        ) {
            val focusManager = LocalFocusManager.current
            Box(
                Modifier
                    .fillMaxSize()
                    .noRippleClickable {
                        when {
                            hideBottomSheetOnClickOutside -> sheetState.hide()
                            platform.isIOS -> focusManager.clearFocus(true)
                        }
                        focusManager.clearFocus(true)
                    }
            ) {
                Surface(
                    modifier = Modifier
                        .offset {
                            IntOffset(x = 0, y = animatedSheetFraction.roundToPx() - imeBottom)
                        }
                        .fillMaxWidth()
                        .height(screenHeight)
                        .noRippleClickable {
                            if (platform.isIOS) {
                                focusManager.clearFocus(true)
                            }
                        },
                    shape = bottomSheetShape,
                    color = bottomSheetColor
                ) {
                    Column(
                        modifier = Modifier.padding(sheetPadding),
                        content = {
                            BottomSheetDefaults.DragHandle(
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                color = LocalSportsRadarTheme.colors.secondaryContainer
                            )
                            bottomSheetContent()
                        }
                    )
                }
            }
        }
    }
}

