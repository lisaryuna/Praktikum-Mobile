package com.example.modul3_compose_baru

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.LocaleListCompat
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

val LavenderBlush = Color(0xFFFFE5EC)
val PinkChampagne = Color(0xFFFFC2D1)
val Watermelon = Color(0xFFFB6F92)

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController)
        }

        composable(
            route = "detail/{songId}",
            arguments = listOf(navArgument("songId") {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val songId = backStackEntry.arguments?.getInt("songId")
            DetailScreen(navController, songId)
        }

        composable("language") {
            LanguageScreen(navController)
        }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LavenderBlush)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                color = Color.Black,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            IconButton(onClick = {
                navController.navigate("language")
            }) {
                Icon(
                    painter = painterResource(android.R.drawable.ic_menu_sort_alphabetically),
                    contentDescription = "Change Language",
                    tint = Watermelon
                )
            }
        }

        LazyRow(contentPadding = PaddingValues(horizontal = 8.dp)) {
            items(SongData.songs) { song ->
                SongItemCard(song, navController, context, Modifier.width(320.dp))
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)) {
            items(SongData.songs) { song ->
                SongItemCard(song, navController, context, Modifier.fillMaxWidth())
            }
        }
    }
}

@Composable
fun SongItemCard(song: Song, navController: NavController, context: android.content.Context, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = PinkChampagne),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier.padding(8.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
            Image(
                painter = painterResource(id = song.imageResId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(110.dp).clip(RoundedCornerShape(16.dp))
            )
            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    song.title,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "${stringResource(R.string.label_album)} ${song.albumName}", color = Color.Black, fontSize = 14.sp)

                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Year: ${song.year}",
                    color = Color.Black,
                    fontSize = 14.sp)

                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(song.externalLink))
                            context.startActivity(intent)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Watermelon),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                    ) { Text("Listen", fontSize = 12.sp) }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = { navController.navigate("detail/${song.id}") },
                        colors = ButtonDefaults.buttonColors(containerColor = Watermelon, contentColor = Color.White),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                    ) { Text("Detail", fontSize = 12.sp) }
                }
            }
        }
    }
}


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
                "Album: ${song.albumName}",
                color = Color.Black,
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
            Text(
                "Release Year: ${song.year}",
                color = Color.Black,
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
            Text(
                text = song.description,
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
            ) { Text("Back to Home") }
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