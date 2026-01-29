package org.sportsradar.sportsradarapp.common.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import org.sportsradar.uiKit.icons.ArrowDown

@Composable
fun SportsRadarAppDropDownField(
    label: String?,
    placeholder: String = "",
    enabled: Boolean = true,
    leftIcon: ImageVector? = null,
    bottomSheetContent: @Composable ColumnScope.(sheetState: SportsRadarAppBottomSheetState) -> Unit
) {
    val sheetState = rememberSportsRadarAppBottomSheetState()
    Box(Modifier.clip(DefaultTextFieldShape)) {
        SportsRadarAppTextField(
            value = label.orEmpty(),
            onValueChange = {},
            leftIcon = leftIcon,
            readOnly = true,
            placeholder = placeholder,
            rightIcon = {
                CommonIconButton(ArrowDown, enabled = enabled) {
                    sheetState.expand()
                }
            }
        )
        Spacer(
            Modifier
                .matchParentSize()
                .clickable(enabled = enabled) { sheetState.expand() }
        )
    }
    SportsRadarAppBottomSheet(
        sheetState = sheetState,
        hideBottomSheetOnClickOutside = true,
        bottomSheetContent = {
            bottomSheetContent(sheetState)
        }
    )
}