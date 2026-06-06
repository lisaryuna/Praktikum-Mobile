package com.example.modul5compose.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.modul5compose.MovieViewModel
import com.example.modul5compose.data.network.ApiConfig
import com.example.modul5compose.data.repository.UiState
import com.example.modul5compose.ui.theme.*
import com.example.modul5compose.R

@Composable
fun DetailScreen(
    navController: NavController,
    movieId: Int?,
    viewModel: MovieViewModel
    ) {

    val movieState by viewModel.movieState.collectAsState()
    val movie = if (movieState is UiState.Success) {
        (movieState as UiState.Success).data.find { it.id == movieId }
    } else null

     if (movie != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LavenderBlush)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = "${ApiConfig.IMAGE_BASE_URL}${movie.posterPath}",
                    contentDescription = "Poster ${movie.title}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(320.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                movie.title,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp
            )
            Text(
                "${stringResource(R.string.label_release_date)} ${movie.releaseDate}",
                color = Color.Black,
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
            Text(
                "${stringResource(R.string.label_rating)} ${movie.voteAverage}/10",
                color = Color.Black,
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
            Text(
                movie.overview,
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
            ) { Text(stringResource(R.string.btn_back)) }
        }
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text((stringResource(R.string.error_not_found)), color = Color.Black)
        }
     }
}
