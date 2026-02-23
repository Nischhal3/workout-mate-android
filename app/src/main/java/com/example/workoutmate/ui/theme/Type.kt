package com.example.workoutmate.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.workoutmate.R

private val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

private val poppinsFont = GoogleFont("Poppins")
private val googleSansFont = GoogleFont("Google Sans")

val Poppins = FontFamily(
    Font(googleFont = poppinsFont, fontProvider = provider, weight = FontWeight.Normal),
    Font(googleFont = poppinsFont, fontProvider = provider, weight = FontWeight.Bold)
)

val GoogleSans = FontFamily(
    Font(googleFont = googleSansFont, fontProvider = provider, weight = FontWeight.Bold)
)

private val CenteredTitle = TextStyle(
    color = DarkGreen,
    fontFamily = GoogleSans,
    fontWeight = FontWeight.Bold,
    textAlign = TextAlign.Center
)

private val body = TextStyle(
    fontSize = 22.sp, fontFamily = Poppins, fontWeight = FontWeight.Normal
)

val CustomTypography = Typography(

    bodyLarge = body.copy(
        fontSize = 22.sp
    ),

    bodyMedium = body.copy(
        fontSize = 18.sp
    ),

    bodySmall = body.copy(
        fontSize = 14.sp
    ),

    titleLarge = CenteredTitle.copy(
        fontSize = 26.sp
    ),

    titleMedium = CenteredTitle.copy(
        fontSize = 22.sp
    ),

    titleSmall = CenteredTitle.copy(
        fontSize = 18.sp
    )
)