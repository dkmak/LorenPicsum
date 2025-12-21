package com.androidapptemplate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.androidapptemplate.ui.theme.AndroidAppTemplateTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HomeScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val viewModel: HomeViewModel = hiltViewModel()
    val uiState by viewModel.pictureUiState.collectAsStateWithLifecycle()
    val gridState = rememberLazyGridState()
    AndroidAppTemplateTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Lorem PicSum") }
                )
            },
            modifier = Modifier.fillMaxSize()
        )
        { innerPadding ->
            when (val state = uiState) {
                is PicturesUiState.Error -> {
                    ErrorMessage(
                        modifier = Modifier.padding(innerPadding),
                        errorMessage = state.message
                    )
                }
                is PicturesUiState.Loading -> {
                    Loading(modifier = Modifier.padding(innerPadding))
                }

                is PicturesUiState.Success -> {
                    HomeContent(
                        modifier = Modifier.padding(innerPadding),
                        pictureList = state.pictures,
                        gridState = gridState
                    )
                }
            }
        }
    }
}


@Composable
fun HomeContent(
    modifier: Modifier,
    pictureList: List<PictureData>,
    gridState: LazyGridState
) {
    LazyVerticalGrid(
        modifier = modifier,
        state = gridState,
        columns = GridCells.Fixed(2),
    ) {
        items(
            pictureList,
            key = { picture -> picture.id }
        ) { picture ->
            PictureItem(picture)
        }
    }
}

@Composable
fun PictureItem(data: PictureData) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { isExpanded = !isExpanded },
        border = BorderStroke(
            width = 2.dp,
            color = Color.DarkGray
        )

    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(128.dp)
                        .padding(bottom = 8.dp),
                    model = data.downloadUrl,
                    contentDescription = "${data.id} + by ${data.author}",
                    contentScale = ContentScale.Fit
                )
            }

            AnimatedVisibility(
                visible = isExpanded,
                enter = fadeIn() + slideInVertically(initialOffsetY = { fullHeight -> -fullHeight / 2 }),
                exit = fadeOut() + slideOutVertically(targetOffsetY = { fullHeight -> -fullHeight / 2 })
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(data.author, modifier = Modifier)
                        Text("ID: ${data.id}", modifier = Modifier.padding(vertical = 4.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun ErrorMessage(
    modifier: Modifier = Modifier,
    errorMessage: String
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = errorMessage)
    }
}

@Composable
fun Loading(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Preview(showBackground = true)
@Composable
fun HomeContentPreview() {
    AndroidAppTemplateTheme {
        HomeContent(
            modifier = Modifier,
            pictureList = listOf(
                PictureData(
                    id = 0,
                    author = "Author #1",
                    width = 128,
                    height = 128,
                    url = "https://unsplash.com/photos/yC-Yzbqy7PY",
                    downloadUrl = "url"
                ),
                PictureData(
                    id = 1,
                    author = "Author #2",
                    width = 128,
                    height = 128,
                    url = "https://unsplash.com/photos/yC-Yzbqy7PY",
                    downloadUrl = "url"
                )
            ),
            gridState = rememberLazyGridState()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingPreview() {
    AndroidAppTemplateTheme {
        Loading()
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorPreview() {
    AndroidAppTemplateTheme {
        PicturesUiState.Error(message = "An unknown error occurred")
    }
}

