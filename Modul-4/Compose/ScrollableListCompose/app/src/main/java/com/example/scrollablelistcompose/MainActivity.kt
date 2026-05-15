package com.example.scrollablelistcompose

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.scrollablelistcompose.model.Movie
import com.example.scrollablelistcompose.viewmodel.MovieViewModel
import com.example.scrollablelistcompose.viewmodel.MovieViewModelFactory
import timber.log.Timber

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val viewModel: MovieViewModel = viewModel(
                factory = MovieViewModelFactory("Muhammad Ilham")
            )

            Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFF1A1A1A)) {
                NavHost(navController = navController, startDestination = "main") {
                    composable("main") {
                        MainScreen(
                            viewModel = viewModel,
                            onDetailClick = { movieTitle ->
                                navController.navigate("detail/$movieTitle")
                            }
                        )
                    }
                    composable("detail/{movieTitle}") { backStackEntry ->
                        val title = backStackEntry.arguments?.getString("movieTitle")
                        DetailScreen(
                            title = title ?: "",
                            viewModel = viewModel,
                            onBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    viewModel: MovieViewModel,
    onDetailClick: (String) -> Unit
) {
    val movies by viewModel.movieList.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(movies) {
        if (movies.isNotEmpty()) {
            Timber.d("Data film berhasil dimuat oleh: Muhammad Ilham. Jumlah data: ${movies.size}")
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            item {
                Text(
                    text = "Featured Movies",
                    color = Color(0xFF6847A3),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(movies.take(5)) { movie ->
                        FeaturedMovieCard(movie = movie) {
                            onDetailClick(movie.title)
                        }
                    }
                }
            }

            item {
                Text(
                    text = "All Movies",
                    color = Color(0xFF6847A3),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 8.dp)
                )
            }

            items(movies) { movie ->
                Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    MovieItem(movie = movie, onDetailClick = { onDetailClick(movie.title) })
                }
            }
        }

        Button(
            onClick = {
                Timber.d("Tombol Explicit Intent (View Profile) ditekan")
                val intent = Intent(context, ProfileActivity::class.java)
                context.startActivity(intent)
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6847A3)),
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp)
                .height(48.dp)
                .width(160.dp),
            elevation = ButtonDefaults.buttonElevation(8.dp)
        ) {
            Text("View Profile", color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun FeaturedMovieCard(movie: Movie, onDetailClick: () -> Unit) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .width(370.dp)
            .height(180.dp)
            .clickable { onDetailClick() },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2A2A2A))
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = movie.imageRes),
                contentDescription = null,
                modifier = Modifier
                    .size(width = 100.dp, height = 150.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = movie.title,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = movie.year,
                    color = Color.Gray,
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = movie.plot,
                    color = Color.LightGray,
                    fontSize = 11.sp,
                    maxLines = 2,
                    lineHeight = 16.sp,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Button(
                        onClick = {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(movie.imdbUrl))
                            context.startActivity(intent)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6847A3)),
                        modifier = Modifier.weight(1f).height(32.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text("IMDB", fontSize = 10.sp)
                    }
                    Button(
                        onClick = {
                            Timber.d("Tombol Detail ditekan pada film: ${movie.title}")
                            onDetailClick()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6847A3)),
                        modifier = Modifier.weight(1f).height(32.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text("Detail", fontSize = 10.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun MovieItem(movie: Movie, onDetailClick: () -> Unit) {
    val context = LocalContext.current
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2A2A2A))
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = movie.imageRes),
                contentDescription = null,
                modifier = Modifier
                    .size(width = 100.dp, height = 140.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(movie.title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp, maxLines = 1)
                Text(movie.year, color = Color.Gray, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(movie.plot, color = Color.LightGray, fontSize = 12.sp, maxLines = 2, overflow = TextOverflow.Ellipsis)
                Spacer(modifier = Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = { context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(movie.imdbUrl))) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6847A3)),
                        modifier = Modifier.weight(1f).height(36.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) { Text("IMDB", fontSize = 12.sp) }
                    Button(
                        onClick = {
                            Timber.d("Tombol Detail ditekan pada film: ${movie.title}")
                            onDetailClick()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6847A3)),
                        modifier = Modifier.weight(1f).height(36.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) { Text("Detail", fontSize = 12.sp) }
                }
            }
        }
    }
}

@Composable
fun DetailScreen(title: String, viewModel: MovieViewModel, onBack: () -> Unit) {
    val movies by viewModel.movieList.collectAsState()
    val movie = movies.find { it.title == title }
    val context = LocalContext.current

    LaunchedEffect(movie) {
        if (movie != null) {
            Timber.d("Data detail film ${movie.title} berhasil dimuat di halaman Detail")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A1A1A))
            .verticalScroll(rememberScrollState())
    ) {
        if (movie != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp, bottom = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = movie.imageRes),
                    contentDescription = null,
                    modifier = Modifier
                        .width(240.dp)
                        .aspectRatio(2f / 3f)
                        .clip(RoundedCornerShape(20.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)) {
                Text(
                    text = movie.title,
                    color = Color.White,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = movie.year,
                    color = Color.Gray,
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Plot:",
                    color = Color(0xFF6847A3),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = movie.plot,
                    color = Color.LightGray,
                    fontSize = 15.sp,
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = onBack,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF333333)),
                        modifier = Modifier.weight(1f).height(48.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Back")
                    }

                    IconButton(
                        onClick = {
                            val sendIntent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, "Nonton ini yuk: ${movie.title}")
                                type = "text/plain"
                            }
                            context.startActivity(Intent.createChooser(sendIntent, null))
                        },
                        modifier = Modifier
                            .size(48.dp)
                            .background(Color(0xFF6847A3), RoundedCornerShape(12.dp))
                    ) {
                        Icon(Icons.Default.Share, contentDescription = "Share", tint = Color.White)
                    }

                    Button(
                        onClick = {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(movie.imdbUrl))
                            context.startActivity(intent)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6847A3)),
                        modifier = Modifier.weight(1f).height(48.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("IMDB")
                    }
                }
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}