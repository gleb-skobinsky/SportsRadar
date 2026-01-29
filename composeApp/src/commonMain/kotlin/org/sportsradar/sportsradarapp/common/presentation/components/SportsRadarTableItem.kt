package org.sportsradar.sportsradarapp.common.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.sportsradar.uiKit.theme.LocalSportsRadarTheme
import org.sportsradar.uiKit.icons.Copy
import org.sportsradar.sportsradarapp.common.presentation.modifiers.noRippleClickable

@Composable
fun SportsRadarAppTableItem(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    copyable: Boolean = false,
) {
    val clipboardManager = LocalClipboardManager.current
    var isTextExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {
        Text(
            text = label,
            style = LocalSportsRadarTheme.typography.bodyLarge.copy(
                textAlign = TextAlign.Start,
                color = LocalSportsRadarTheme.colors.primaryContainer
            )
        )
        4.dp.VerticalSpacer()
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = value,
                style = LocalSportsRadarTheme.typography.bodyMedium.copy(
                    textAlign = TextAlign.Start,
                    color = LocalSportsRadarTheme.colors.primaryContainer
                ),
                overflow = if (isTextExpanded) TextOverflow.Clip else TextOverflow.Ellipsis,
                maxLines = if (isTextExpanded) Int.MAX_VALUE else 1,
                modifier = Modifier
                    .weight(1f)
                    .noRippleClickable { isTextExpanded = !isTextExpanded }
                    .animateContentSize()
            )
            if (copyable) {
                8.dp.HorizontalSpacer()
                IconButton(
                    modifier = Modifier
                        .size(24.dp),
                    onClick = {
                        clipboardManager.setText(
                            annotatedString = AnnotatedString(value)
                        )
                    }
                ) {
                    Icon(
                        modifier = Modifier
                            .size(24.dp),
                        imageVector = Copy,
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                }
            }
        }
    }
}