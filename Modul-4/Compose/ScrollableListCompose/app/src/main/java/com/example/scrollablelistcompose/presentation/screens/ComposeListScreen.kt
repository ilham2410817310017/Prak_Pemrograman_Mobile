package com.example.scrollablelistcompose.presentation.screens

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.scrollablelistcompose.ProfileActivity
import com.example.scrollablelistcompose.model.Movie
import com.example.scrollablelistcompose.viewmodel.MovieViewModel
import timber.log.Timber

@Composable
fun ComposeListScreen(viewModel: MovieViewModel) {
    val context = LocalContext.current

    // Syarat Poin c: Mengamati data dari StateFlow
    val movieList by viewModel.movieList.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            // Memberikan space di bawah agar list tidak tertutup tombol kapsul (Mirip View 70dp di XML)
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            // Bagian Header Featured (Horizontal)
            item {
                Text(
                    text = "Featured Movies",
                    modifier = Modifier.padding(16.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF6847A3)
                )
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(movieList.take(5)) { movie ->
                        MovieHorizontalItem(movie) {
                            // Poin d.3: Log item sebelum pindah detail
                            Timber.d("Pindah ke detail: ${movie.title}")
                            viewModel.onMovieClicked(movie)
                        }
                    }
                }
            }

            // Bagian Header All Movies (Vertical)
            item {
                Text(
                    text = "All Movies",
                    modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 8.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF6847A3)
                )
            }

            // List Vertical
            items(movieList) { movie ->
                MovieVerticalItem(movie) {
                    Timber.d("Pindah ke detail: ${movie.title}")
                    viewModel.onMovieClicked(movie)
                }
            }
        }

        // Tombol Explicit Intent Kapsul (Poin d.b)
        Button(
            onClick = {
                Timber.i("Tombol Explicit Intent ditekan di Compose")
                val intent = Intent(context, ProfileActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp)
                .height(56.dp)
                .width(180.dp),
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE)),
            elevation = ButtonDefaults.buttonElevation(8.dp)
        ) {
            Text("View Profile", color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun MovieVerticalItem(movie: Movie, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = movie.imageRes),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(start = 16.dp).weight(1f)) {
                Text(text = movie.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = movie.plot, maxLines = 2, fontSize = 12.sp, color = Color.Gray)
                Button(
                    onClick = onClick,
                    modifier = Modifier.padding(top = 8.dp).height(32.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp)
                ) {
                    Text("Detail", fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun MovieHorizontalItem(movie: Movie, onClick: () -> Unit) {
    Card(
        modifier = Modifier.width(200.dp).height(120.dp),
        shape = RoundedCornerShape(12.dp),
        onClick = onClick
    ) {
        Box {
            Image(
                painter = painterResource(id = movie.imageRes),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Surface(
                modifier = Modifier.align(Alignment.BottomStart).fillMaxWidth(),
                color = Color.Black.copy(alpha = 0.6f)
            ) {
                Text(
                    text = movie.title,
                    color = Color.White,
                    modifier = Modifier.padding(8.dp),
                    fontSize = 14.sp,
                    maxLines = 1
                )
            }
        }
    }
}