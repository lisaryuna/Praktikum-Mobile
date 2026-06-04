package com.example.modul5compose

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: SongViewModel = viewModel(factory = SongViewModelFactory("Koleksi Music Compose"))
) {
    val context = LocalContext.current
    val songList by viewModel.songList.collectAsState()
    val navEvent by viewModel.navigationEvent.collectAsState()
    val intentEvent by viewModel.intentEvent.collectAsState()

    LaunchedEffect(navEvent) {
        navEvent?.let { song ->
            navController.navigate("detail/${song.id}")
            viewModel.onNavigationHandled()
        }
    }

    LaunchedEffect(intentEvent) {
        intentEvent?.let { link ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            context.startActivity(intent)
            viewModel.onIntentHandled()
        }
    }

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
                text = stringResource(id = _root_ide_package_.androidx.compose.material3.R.string.app_name),
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

        LazyRow(contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            _root_ide_package_.androidx.compose.foundation.lazy.LazyListScope.items(songList) { song ->
                SongItemCard(
                    song = song,
                    onDetailClick = { viewModel.onDetailClicked(song) },
                    onLinkClick = { viewModel.onIntentClicked(song.externalLink) },
                    _root_ide_package_.androidx.compose.ui.Modifier.Companion.fillParentMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)) {
            _root_ide_package_.androidx.compose.foundation.lazy.LazyListScope.items(songList) { song ->
                SongItemCard(
                    song = song,
                    onDetailClick = { viewModel.onDetailClicked(song) },
                    onLinkClick = { viewModel.onIntentClicked(song.externalLink) },
                    Modifier.fillMaxWidth()
                )
            }
        }
    }
}