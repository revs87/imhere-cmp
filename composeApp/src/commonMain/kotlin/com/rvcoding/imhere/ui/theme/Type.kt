package com.rvcoding.imhere.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import imhere.composeapp.generated.resources.Res
import imhere.composeapp.generated.resources.inter18pt_bold
import imhere.composeapp.generated.resources.inter18pt_light
import imhere.composeapp.generated.resources.inter18pt_medium
import imhere.composeapp.generated.resources.inter18pt_regular
import imhere.composeapp.generated.resources.inter18pt_semibold
import imhere.composeapp.generated.resources.readexpro_bold
import imhere.composeapp.generated.resources.readexpro_light
import imhere.composeapp.generated.resources.readexpro_medium
import imhere.composeapp.generated.resources.readexpro_regular
import imhere.composeapp.generated.resources.readexpro_semibold
import org.jetbrains.compose.resources.Font


@Composable
fun AppTypography() = InterTypography()


@Composable
fun ReadexProFontFamily() = FontFamily(
    Font(Res.font.readexpro_light, weight = FontWeight.Light),
    Font(Res.font.readexpro_regular, weight = FontWeight.Normal),
    Font(Res.font.readexpro_medium, weight = FontWeight.Medium),
    Font(Res.font.readexpro_semibold, weight = FontWeight.SemiBold),
    Font(Res.font.readexpro_bold, weight = FontWeight.Bold)
)

@Composable
fun ReadexProTypography() = Typography().run {
    val fontFamily = ReadexProFontFamily()
    copy(
        displayLarge = displayLarge.copy(fontFamily = fontFamily),
        displayMedium = displayMedium.copy(fontFamily = fontFamily),
        displaySmall = displaySmall.copy(fontFamily = fontFamily),
        headlineLarge = headlineLarge.copy(fontFamily = fontFamily),
        headlineMedium = headlineMedium.copy(fontFamily = fontFamily),
        headlineSmall = headlineSmall.copy(fontFamily = fontFamily),
        titleLarge = titleLarge.copy(fontFamily = fontFamily),
        titleMedium = titleMedium.copy(fontFamily = fontFamily),
        titleSmall = titleSmall.copy(fontFamily = fontFamily),
        bodyLarge = bodyLarge.copy(fontFamily = fontFamily),
        bodyMedium = bodyMedium.copy(fontFamily = fontFamily),
        bodySmall = bodySmall.copy(fontFamily = fontFamily),
        labelLarge = labelLarge.copy(fontFamily = fontFamily),
        labelMedium = labelMedium.copy(fontFamily = fontFamily),
        labelSmall = labelSmall.copy(fontFamily = fontFamily)
    )
}

@Composable
fun InterFontFamily() = FontFamily(
    Font(Res.font.inter18pt_light, weight = FontWeight.Light),
    Font(Res.font.inter18pt_regular, weight = FontWeight.Normal),
    Font(Res.font.inter18pt_medium, weight = FontWeight.Medium),
    Font(Res.font.inter18pt_semibold, weight = FontWeight.SemiBold),
    Font(Res.font.inter18pt_bold, weight = FontWeight.Bold)
)

@Composable
fun InterTypography() = Typography().run {
    val fontFamily = InterFontFamily()
    copy(
        displayLarge = displayLarge.copy(fontFamily = fontFamily),
        displayMedium = displayMedium.copy(fontFamily = fontFamily),
        displaySmall = displaySmall.copy(fontFamily = fontFamily),
        headlineLarge = headlineLarge.copy(fontFamily = fontFamily),
        headlineMedium = headlineMedium.copy(fontFamily = fontFamily),
        headlineSmall = headlineSmall.copy(fontFamily = fontFamily),
        titleLarge = titleLarge.copy(fontFamily = fontFamily),
        titleMedium = titleMedium.copy(fontFamily = fontFamily),
        titleSmall = titleSmall.copy(fontFamily = fontFamily),
        bodyLarge = bodyLarge.copy(fontFamily = fontFamily),
        bodyMedium = bodyMedium.copy(fontFamily = fontFamily),
        bodySmall = bodySmall.copy(fontFamily = fontFamily),
        labelLarge = labelLarge.copy(fontFamily = fontFamily),
        labelMedium = labelMedium.copy(fontFamily = fontFamily),
        labelSmall = labelSmall.copy(fontFamily = fontFamily)
    )
}