package com.example.popularmovie.presentation

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.content.ContextCompat.startActivity
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.example.domainmodule.model.FavoritesItem
import com.example.domainmodule.model.Movie
import com.example.domainmodule.model.MovieDetailInfo
import com.example.popularmovie.POSTER_BASE_URL
import com.example.popularmovie.R
import com.example.popularmovie.W185
import com.example.popularmovie.W500
import com.example.popularmovie.YOUTUBE_TN_URL
import com.example.popularmovie.YOUTUBE_TRAILERS_URL
import com.example.popularmovie.presentation.ui.theme.PopularMovieComposeTheme
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(viewModelInstance: MainViewModel = koinViewModel(), movieId: Int, onNavBack: () -> Unit) {

    /*
    to call the service, first method:
    viewModelInstance.getSingleMovie(movieId)  //it is not necessary to launch it inside LaunchedEffect
    val result by viewModelInstance.movie.collectAsStateWithLifecycle()
    DetailContent(result)

    //alternatively (the state changes only if it is modified)
    val movie by derivedStateOf {
        viewModelInstance.movie.collectAsStateWithLifecycle().value
    }
    */

    //to call the service, second method, using produceState to call a sospending function:
    val movieDetailInfo by produceState<MovieDetailInfo?>(initialValue = null) {
        value = viewModelInstance.suspendGetSingleMovie(movieId)
    }
    Scaffold(
        topBar = {
            TopAppBar(
               title = {Text("Preview")},
               navigationIcon= {
                   IconButton(
                       onClick = {
                            onNavBack()
                   }) {
                       Icon(Icons.Filled.ArrowBack, "backIcon")
                   }
               },
               colors = TopAppBarDefaults.topAppBarColors(containerColor = colorScheme.primaryContainer)
            )
        },
        content = { paddingValues->
            DetailContent(movieDetailInfo, paddingValues, viewModelInstance)
        },
    )

}


@Composable
fun DetailContent(movieDetailInfo: MovieDetailInfo?, paddingValues: PaddingValues, viewModelInstance: MainViewModel?) {
    if (movieDetailInfo == null) return

    var favBtnClicked by rememberSaveable{
        mutableStateOf(movieDetailInfo.favorite)
    }

    val myMovie = movieDetailInfo.movie
    val url = POSTER_BASE_URL +W500+myMovie.backdrop_path
    val url2 = POSTER_BASE_URL +W185+myMovie.poster_path

    Surface(color = colorScheme.background, modifier = Modifier.padding(paddingValues)) {
        Column(
            modifier = Modifier.verticalScroll(state = rememberScrollState(), enabled = true)
        ) {
            Box(
                Modifier
                    .height(220.dp)
                    .fillMaxWidth()
            ) {
                val context = LocalContext.current
                val painter = remember {
                    ImageRequest.Builder(context)
                        .data(url)
                        .size(Size.ORIGINAL)
                        .crossfade(false)
                        .build()
                }
                AsyncImage(model = painter,
                    contentDescription ="",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Row(
                Modifier
                    .height(IntrinsicSize.Max)
                    .padding(top = 8.dp)
                    .fillMaxWidth()
            ) {
                Box(
                    Modifier
                        .height(124.dp)
                        .width(86.dp)
                ) {
                    AsyncImage(model = ImageRequest.Builder(LocalContext.current)
                        .data(url2)
                        .size(Size.ORIGINAL)
                        .crossfade(false)
                        .build(),
                        contentDescription ="",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 8.dp)
                    )
                }
                val release_date = SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(myMovie.release_date)
                val italianFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ITALIAN).format(release_date)

                Column(modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(1.0f)) {
                    Text(myMovie.title, overflow = TextOverflow.Ellipsis, maxLines = 2)
                    Text(italianFormat)
                    RatingBar(modifier = Modifier.height(20.dp), stars = 10, rating = myMovie.vote_average, starsColor = Color.Red)
                    Text("${myMovie.vote_count}/10")
                }

                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .height(25.dp)
                        .width(25.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(
                                color = Color.Red
                            ),
                        ) {
                            favBtnClicked = !favBtnClicked
                            viewModelInstance?.UpdateFavMovie(
                                FavoritesItem(myMovie.id, myMovie.poster_path),
                                favBtnClicked
                            )
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        painter = painterResource(
                            if (favBtnClicked)
                                R.drawable.ic_favorite_red_24dp
                            else
                                R.drawable.ic_favorite_border_white_24dp
                        ),
                        //Icons.Outlined.FavoriteBorder,
                        contentDescription = "",
                        modifier = Modifier
                            .height(20.dp)
                            .fillMaxWidth()
                            .clip(CircleShape),
                        tint = Color.Red
                    )
                }
                Box(
                    Modifier
                        .width(8.dp)
                        .fillMaxHeight(1f))
            }
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(all = 8.dp)) {
                Text(text = myMovie.overview, fontSize = 13.sp)
            }

            if (movieDetailInfo.videos.size>0) {
                Text(
                    "Trailers",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 4.dp, start = 8.dp, end = 8.dp)
                        .background(colorScheme.tertiaryContainer, shape = CircleShape)
                )
            } else {
                Text(
                    "No available trailers",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color.Gray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp, bottom = 4.dp)
                        .background(colorScheme.tertiaryContainer, shape = CircleShape)
                        .padding(top = 8.dp, bottom = 8.dp)
                )
            }
            val context = LocalContext.current
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp, start = 8.dp, end = 8.dp)
            ) {
                items(movieDetailInfo.videos) {
                    val urlthumbnail = YOUTUBE_TN_URL + it.key + "/hqdefault.jpg"
                    //val url = "https://www.youtube.com/watch?v=" + it.key
                    val painter = ImageRequest.Builder(LocalContext.current)
                            .data(urlthumbnail)
                            .size(Size.ORIGINAL) // Set the target size to load the image at.
                            .build()
                    Box(contentAlignment = Alignment.Center,
                    modifier = Modifier.clickable {
                        val trailersUrl = YOUTUBE_TRAILERS_URL + it.key
                        val i = Intent(Intent.ACTION_VIEW, Uri.parse(trailersUrl))
                        //i.data = Uri.parse(trailersUrl)
                        startActivity(context, i, null)
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_play_svg),
                            contentDescription = "",
                            tint = Color.White,
                            modifier = Modifier
                                .alpha(1f)
                                .zIndex(10f)
                                .scale(1.5f)
                                .alpha(0.4f),
                        )
                        AsyncImage(
                            model = painter,
                            contentDescription = "",
                            contentScale = ContentScale.Inside,
                            //modifier = Modifier.width(200.dp).height(100.dp)
                            modifier = Modifier
                                .height(100.dp)
                                .zIndex(0f),
                        )
                    }
                }
            }

            if (movieDetailInfo.review.size>=1) {
                Text(
                    "Reviews",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp, start = 8.dp, end = 8.dp)
                        .background(colorScheme.tertiaryContainer, shape = CircleShape)

                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp, start = 8.dp, end = 8.dp)
                    .background(colorScheme.tertiaryContainer, shape = RoundedCornerShape(8.dp))
            ) {
                for (i in (0..movieDetailInfo.review.size-1)) {
                    Text(
                        text = movieDetailInfo.review[i].content,
                        fontSize = 12.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        color = colorScheme.onTertiaryContainer,
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .clickable {
                                val reviewsUrl = movieDetailInfo.review[i].url
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(reviewsUrl))
                                startActivity(context, intent, null)
                            }
                    )
                    Divider()
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreviewDetail() {
    PopularMovieComposeTheme {
        DetailContent(
            MovieDetailInfo(
                emptyList(),
                emptyList(),
                Movie(
                    true,
                    "",
                    emptyList(),
                    12,
                    "",
                    "hello",
                    "a little brief description of the content of the movie, lorem ipsum dolor samet",
                    10.0,
                    "",
                    "2023-10-10",
                    "hello",
                    false,
                    5.4,
                    12,
                ),
                true
            ),
            PaddingValues(all = 0.dp),
            null
        )
    }
}