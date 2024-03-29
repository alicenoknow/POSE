package com.example.pose.ui.theme

import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import com.example.pose.R


@OptIn(ExperimentalTextApi::class)
val JostFont = GoogleFont(name = "Jost")

@OptIn(ExperimentalTextApi::class)
val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

@OptIn(ExperimentalTextApi::class)
val JostFontFamily = FontFamily(
    Font(googleFont = JostFont, fontProvider = provider),
    Font(googleFont = JostFont, fontProvider = provider, weight = FontWeight.Medium),
    Font(googleFont = JostFont, fontProvider = provider, weight = FontWeight.Bold)
)
