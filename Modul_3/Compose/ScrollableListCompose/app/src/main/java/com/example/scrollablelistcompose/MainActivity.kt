package com.example.scrollablelistcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.scrollablelistcompose.ui.MovieItem
import com.example.scrollablelistcompose.viewmodel.MovieViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.ui.platform.LocalConfiguration
import android.content.res.Configuration

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFF1A1A1A)) {
                NavHost(navController = navController, startDestination = "main") {
                    composable("main") {
                        MainScreen(onDetailClick = { movieTitle ->
                            navController.navigate("detail/$movieTitle")
                        })
                    }
                    composable("detail/{movieTitle}") { backStackEntry ->
                        val title = backStackEntry.arguments?.getString("movieTitle")
                        DetailScreen(title = title ?: "", onBack = { navController.popBackStack() })
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    viewModel: MovieViewModel = viewModel(),
    onDetailClick: (String) -> Unit
) {
    val movies by viewModel.movies
    val configuration = LocalConfiguration.current

    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    LazyVerticalGrid(
        columns = if (isLandscape) GridCells.Fixed(2) else GridCells.Fixed(1),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            Column {
                Text(
                    text = "Featured Movies",
                    color = Color(0xFF6847A3),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(movies) { movie ->
                        MovieItem(
                            movie = movie,
                            isHorizontal = true,
                            onDetailClick = { onDetailClick(movie.title) }
                        )
                    }
                }
            }
        }

        item(span = { GridItemSpan(maxLineSpan) }) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "All Movies",
                color = Color(0xFF6847A3),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        items(movies) { movie ->
            MovieItem(
                movie = movie,
                isHorizontal = false,
                onDetailClick = { onDetailClick(movie.title) }
            )
        }
    }
}

@Composable
fun DetailScreen(
    title: String,
    viewModel: MovieViewModel = viewModel(),
    onBack: () -> Unit
) {
    val movie = viewModel.movies.value.find { it.title == title }

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
                    .padding(top = 24.dp, bottom = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = movie.imageRes),
                    contentDescription = null,
                    modifier = Modifier
                        .width(300.dp)
                        .aspectRatio(100f / 140f)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = movie.title,
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = movie.year,
                        color = Color(0xFFBCBCBC),
                        fontSize = 18.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Plot:",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = movie.plot,
                    color = Color(0xFFBCBCBC),
                    fontSize = 14.sp,
                    lineHeight = 22.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}