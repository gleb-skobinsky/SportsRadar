package org.sportsradar.sportsradarapp.previews.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.sportsradar.sportsradarapp.common.presentation.components.SportsRadarAppTableItem

@Preview(
    showBackground = true
)
@Composable
fun SportsRadarAppTableItemPreview() {
    SportsRadarAppTableItem(
        label = "Currency",
        value = "999,99",
        copyable = true
    )
}