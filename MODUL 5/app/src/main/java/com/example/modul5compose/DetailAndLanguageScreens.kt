package com.example.modul5compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.LocaleListCompat
import androidx.navigation.NavController

@Composable
fun DetailScreen(navController: NavController, songId: Int?) {
    val song = SongData.songs.find { it.id == songId }

    if (song != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LavenderBlush)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = song.imageResId),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(320.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                song.title,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp
            )
            Text(
                "${stringResource(_root_ide_package_.androidx.compose.material3.R.string.label_album)} ${song.albumName}",
                color = Color.Black,
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
            Text(
                "${stringResource(_root_ide_package_.androidx.compose.material3.R.string.label_year)} ${song.year}",
                color = Color.Black,
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
            Text(
                text = stringResource(song.descriptionResId),
                color = Color.Black,
                fontSize = 15.sp,
                lineHeight = 22.sp,
                modifier = Modifier.padding(top = 24.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = { navController.popBackStack()},
                colors = ButtonDefaults.buttonColors(containerColor = Watermelon, contentColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            ) { Text(stringResource(_root_ide_package_.androidx.compose.material3.R.string.btn_back)) }
        }
    }
}

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
            text = stringResource(_root_ide_package_.androidx.compose.material3.R.string.setting_language),
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