package org.sportsradar.sportsradarapp.settings.presentation

import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.themeanimator.ThemeAnimationFormat
import io.github.themeanimator.ThemeAnimationScope
import io.github.themeanimator.ThemeAnimationState
import io.github.themeanimator.button.ThemeSwitchButton
import io.github.themeanimator.rememberThemeAnimationState
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.sportsradar.sportsradarapp.common.localization.LocaleProvider
import org.sportsradar.sportsradarapp.common.localization.ReactiveLocaleScope
import org.sportsradar.sportsradarapp.common.navigation.KMPNavigator
import org.sportsradar.sportsradarapp.common.navigation.LocalKmpNavigator
import org.sportsradar.sportsradarapp.common.presentation.components.SportsRadarScaffold
import org.sportsradar.sportsradarapp.common.presentation.components.VerticalSpacer
import org.sportsradar.sportsradarapp.resources.AppRes
import org.sportsradar.sportsradarapp.resources.locale_setting
import org.sportsradar.sportsradarapp.resources.settings
import org.sportsradar.sportsradarapp.resources.theme_setting
import org.sportsradar.uiKit.components.SportsRadarTopBar
import org.sportsradar.uiKit.theme.LocalSportsRadarTheme

@Composable
internal fun SettingsScreen() {
    val themeViewModel = sportsRadarThemeViewModel()
    val localeViewModel: LocaleViewModel = koinViewModel()
    SettingsScreenContent(
        animationState = rememberThemeAnimationState(
            themeProvider = themeViewModel,
            format = ThemeAnimationFormat.CircularAroundPress,
            animationSpec = tween(600)
        ),
        localeProvider = localeViewModel,
        navigator = LocalKmpNavigator.current
    )
}

@Composable
private fun SettingsScreenContent(
    localeProvider: LocaleProvider,
    animationState: ThemeAnimationState,
    navigator: KMPNavigator,
) {
    var menuExpanded by rememberSaveable { mutableStateOf(false) }
    val locale by localeProvider.locale.collectAsStateWithLifecycle()
    ReactiveLocaleScope(localeProvider) {
        ThemeAnimationScope(
            state = animationState
        ) {
            SportsRadarScaffold(
                topBar = {
                    SportsRadarTopBar(
                        title = stringResource(AppRes.string.settings),
                        onBackClick = navigator::goBack
                    )
                }
            ) {
                40.dp.VerticalSpacer()
                SettingsRow(
                    stringResource(AppRes.string.theme_setting)
                ) {
                    ThemeSwitchButton(
                        animationState = animationState,
                        iconTint = LocalSportsRadarTheme.colors.secondary
                    )
                }
                12.dp.VerticalSpacer()
                SettingsRow(
                    stringResource(AppRes.string.locale_setting)
                ) {
                    Box {
                        Text(
                            text = stringResource(locale.label),
                            style = LocalSportsRadarTheme.typography.bodySmall,
                            color = LocalSportsRadarTheme.colors.secondary,
                            fontSize = 20.sp,
                            modifier = Modifier.clickable {
                                menuExpanded = true
                            }
                        )
                        DropdownMenu(
                            expanded = menuExpanded,
                            onDismissRequest = {
                                menuExpanded = false
                            }
                        ) {
                            SportsRadarLocale.entries.forEach { lang ->
                                DropdownMenuItem(
                                    text = {
                                        Text(text = stringResource(lang.label))
                                    },
                                    onClick = {
                                        menuExpanded = false
                                        localeProvider.updateLocale(lang)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private inline fun SettingsRow(
    label: String,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        content = {
            Text(
                text = label,
                style = LocalSportsRadarTheme.typography.bodySmall,
                color = LocalSportsRadarTheme.colors.secondary,
                fontSize = 20.sp
            )
            content()
        }
    )
}