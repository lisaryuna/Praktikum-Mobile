package com.example.modul5compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable(
            route = "detail/{songId}",
            arguments = listOf(navArgument("songId") {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val songId = backStackEntry.arguments?.getInt("songId")
            DetailScreen(navController, songId)
        }

        composable("language") { LanguageScreen(navController) }
    }
}

@Composable
fun SongItemCard(song: Song, onDetailClick: () -> Unit, onLinkClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = PinkChampagne),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier
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
                    fontSize = 15.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "${stringResource(_root_ide_package_.androidx.compose.material3.R.string.label_album)} ${song.albumName}", color = Color.Black, fontSize = 14.sp)

                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = onLinkClick,
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Watermelon),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                    ) { Text(stringResource(_root_ide_package_.androidx.compose.material3.R.string.btn_listen), fontSize = 12.sp) }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = onDetailClick,
                        colors = ButtonDefaults.buttonColors(containerColor = Watermelon, contentColor = Color.White),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                    ) { Text(stringResource(_root_ide_package_.androidx.compose.material3.R.string.btn_detail), fontSize = 12.sp) }
                }
            }
        }
    }
}
