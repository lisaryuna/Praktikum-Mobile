package com.example.modul5compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.LocaleListCompat
import androidx.navigation.NavController

@Composable
fun LanguageScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LavenderBlush)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.setting_language),
            color = Color.Black,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        Button(
            onClick = { setAppLocale("en") },
            colors = ButtonDefaults.buttonColors(containerColor = Watermelon, contentColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) { Text("English")}

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { setAppLocale("id") },
            colors = ButtonDefaults.buttonColors(containerColor = Watermelon, contentColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) { Text("Indonesia")}
    }
}

fun setAppLocale(languageTag: String) {
    val localeList = LocaleListCompat.forLanguageTags(languageTag)
    AppCompatDelegate.setApplicationLocales(localeList)
}