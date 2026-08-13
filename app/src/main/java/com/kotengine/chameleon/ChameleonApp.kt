package com.kotengine.chameleon

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kotengine.chameleon.data.DEVICE_PROFILES
import com.kotengine.chameleon.data.DeviceProfile
import com.kotengine.chameleon.data.ORIGINAL_DEVICE
import com.kotengine.chameleon.ui.glass.DeviceAvatar
import com.kotengine.chameleon.ui.glass.GlassBackground
import com.kotengine.chameleon.ui.glass.LiquidButton
import com.kotengine.chameleon.ui.glass.LiquidToggle
import com.kotengine.chameleon.webview.SpoofWebViewScreen

private enum class Screen { PICKER, BROWSER }

@Composable
fun ChameleonApp() {
    var spoofEnabled by remember { mutableStateOf(false) }
    var selectedProfile by remember { mutableStateOf(DEVICE_PROFILES.first()) }
    var screen by remember { mutableStateOf(Screen.PICKER) }
    var targetUrl by remember { mutableStateOf("https://www.whatismybrowser.com/detect/what-is-my-user-agent") }

    val activeProfile = if (spoofEnabled) selectedProfile else ORIGINAL_DEVICE

    GlassBackground {
        backdrop ->
        when (screen) {
            Screen.PICKER -> DevicePickerScreen(
                backdrop = backdrop,
                spoofEnabled = spoofEnabled,
                onSpoofEnabledChange = { spoofEnabled = it },
                profiles = DEVICE_PROFILES,
                selectedProfile = selectedProfile,
                onSelectProfile = { selectedProfile = it },
                onOpenBrowser = { screen = Screen.BROWSER },
            )

            Screen.BROWSER -> Column(Modifier.fillMaxSize()) {
                Column(Modifier.statusBarsPadding().padding(12.dp)) {
                    LiquidButton(onClick = { screen = Screen.PICKER }, backdrop = backdrop) {
                        Text("← Назад", color = Color.White)
                    }
                }
                SpoofWebViewScreen(
                    url = targetUrl,
                    profile = activeProfile,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

@Composable
private fun DevicePickerScreen(
    backdrop: com.kyant.backdrop.Backdrop,
    spoofEnabled: Boolean,
    onSpoofEnabledChange: (Boolean) -> Unit,
    profiles: List<DeviceProfile>,
    selectedProfile: DeviceProfile,
    onSelectProfile: (DeviceProfile) -> Unit,
    onOpenBrowser: () -> Unit,
) {
    Column(
        Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        Text(
            "Chameleon",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
        )
        Text(
            if (spoofEnabled) "Подмена включена: ${selectedProfile.marketingName}" else "Подмена выключена",
            color = Color.White.copy(alpha = 0.7f),
        )

        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
        ) {
            Text("Включить подмену", color = Color.White)
            LiquidToggle(
                selected = { spoofEnabled },
                onSelect = onSpoofEnabledChange,
                backdrop = backdrop,
                modifier = Modifier.padding(top = 8.dp),
            )
        }

        Text("Устройство (Android)", color = Color.White, fontWeight = FontWeight.SemiBold)

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(profiles) { profile ->
                val isSelected = profile.id == selectedProfile.id
                LiquidButton(
                    onClick = { onSelectProfile(profile) },
                    backdrop = backdrop,
                    modifier = Modifier.fillMaxWidth(),
                    tint = if (isSelected) Color(0xFF34C759) else Color.Unspecified,
                ) {
                    DeviceAvatar(id = profile.id)
                    Column(Modifier.padding(vertical = 4.dp)) {
                        Text(profile.marketingName, color = Color.White, fontWeight = FontWeight.Medium)
                        Text(profile.chModel, color = Color.White.copy(alpha = 0.6f), fontSize = 12.sp)
                    }
                }
            }
        }

        LiquidButton(
            onClick = onOpenBrowser,
            backdrop = backdrop,
            modifier = Modifier.fillMaxWidth(),
            surfaceColor = Color(0xFF5B4FE8),
        ) {
            Text("Открыть браузер", color = Color.White, fontWeight = FontWeight.SemiBold)
        }
    }
}
