package com.example.scrollablelistcompose.feature.movie.presentation.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.scrollablelistcompose.ProfileActivity
import com.example.scrollablelistcompose.feature.movie.data.local.entity.MovieEntity
import com.example.scrollablelistcompose.feature.movie.presentation.viewModel.MovieViewModel
import timber.log.Timber

@Composable
fun MainScreen(
    viewModel: MovieViewModel,
    onDetailClick: (String) -> Unit
) {
    val context = LocalContext.current
    val movieList by viewModel.movieList.collectAsState()

    var lastViewedMovie by remember { mutableStateOf("Memuat...") }

    LaunchedEffect(Unit) {
        lastViewedMovie = viewModel.getLastViewedMovie()
    }

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFF121212))) {
        LazyColumn(
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF2C2C2C)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Terakhir Dilihat: $lastViewedMovie",
                        modifier = Modifier.padding(16.dp),
                        color = Color(0xFFB388FF),
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            item {
                Text(
                    text = "Featured Movies",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFB388FF)
                )
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(movieList.take(5)) { movie ->

                        MovieItemCard(
                            movie = movie,
                            modifier = Modifier.width(375.dp),
                            onDetailClick = {
                                viewModel.saveLastViewedMovie(movie.title)
                                onDetailClick(movie.title)
                            }
                        )
                    }
                }
            }

            item {
                Text(
                    text = "All Movies",
                    modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 8.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFB388FF)
                )
            }

            items(movieList) { movie ->
                MovieItemCard(
                    movie = movie,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                    onDetailClick = {
                        viewModel.saveLastViewedMovie(movie.title)
                        onDetailClick(movie.title)
                    }
                )
            }
        }

        Button(
            onClick = {
                val intent = Intent(context, ProfileActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp)
                .height(56.dp)
                .width(180.dp),
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF673AB7)),
            elevation = ButtonDefaults.buttonElevation(8.dp)
        ) {
            Text("View Profile", color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun MovieItemCard(
    movie: MovieEntity,
    modifier: Modifier = Modifier,
    onDetailClick: () -> Unit
) {
    val context = LocalContext.current

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2C2C2C)), // Abu-abu gelap
    ) {
        Row(modifier = Modifier.padding(12.dp)) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
                contentDescription = null,
                modifier = Modifier
                    .width(100.dp)
                    .height(140.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = movie.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.White,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )

                    val year = movie.releaseDate.take(4)
                    Text(
                        text = year,
                        color = Color.Gray,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = movie.overview,
                    maxLines = 3,
                    fontSize = 12.sp,
                    color = Color.LightGray,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = {
                            val tmdbUrl = "https://www.themoviedb.org/movie/${movie.id}"
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(tmdbUrl))
                            context.startActivity(intent)
                        },
                        modifier = Modifier.height(36.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF673AB7)),
                        shape = RoundedCornerShape(50),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp)
                    ) {
                        Text("TMDB", fontSize = 12.sp, color = Color.White)
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = onDetailClick,
                        modifier = Modifier.height(36.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF673AB7)),
                        shape = RoundedCornerShape(50),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp)
                    ) {
                        Text("Detail", fontSize = 12.sp, color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun DetailScreen(title: String, viewModel: MovieViewModel, onBack: () -> Unit) {
    val movies by viewModel.movieList.collectAsState()
    val movie = movies.find { it.title == title }

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFF121212)).verticalScroll(rememberScrollState())) {
        if (movie != null) {
            Box(modifier = Modifier.fillMaxWidth().padding(top = 40.dp, bottom = 10.dp), contentAlignment = Alignment.Center) {
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w500${movie.posterPath}", contentDescription = null,
                    modifier = Modifier.width(240.dp).aspectRatio(2f / 3f).clip(RoundedCornerShape(20.dp)), contentScale = ContentScale.Crop
                )
            }
            Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)) {
                Text(text = movie.title, color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.Bold)
                Text(text = movie.releaseDate, color = Color.Gray, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = "Plot:", color = Color(0xFFB388FF), fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(text = movie.overview, color = Color.LightGray, fontSize = 15.sp, lineHeight = 22.sp)
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = onBack, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2C2C2C)),
                    modifier = Modifier.fillMaxWidth().height(48.dp), shape = RoundedCornerShape(12.dp)
                ) { Text("Back", color = Color.White) }
            }
        }
    }
}