package com.example.mycomposem3playground.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.example.mycomposem3playground.POSTER_BASE_URL
import com.example.mycomposem3playground.R
import com.example.mycomposem3playground.Routes
import com.example.mycomposem3playground.W185
import com.example.mycomposem3playground.data.remote.dtos.Movie
import com.example.mycomposem3playground.presentation.ui.theme.MyComposeM3PlayGroundTheme
import org.koin.androidx.compose.koinViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyComposeM3PlayGroundTheme {
                MyScaffoldWidget()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun MyScaffoldWidget() {

    val navController = rememberNavController()
    var showTopAppBar by rememberSaveable { mutableStateOf(true) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val destRoute = (navBackStackEntry?.destination?.route?:"")
    showTopAppBar = when {
        destRoute.startsWith(Routes.MainScreen.route) -> true // on this screen bottom bar should be hidden
        destRoute.startsWith(Routes.DetailScreen.route) -> false // here too
        else -> true // in all other cases show bottom bar
    }

    var selection: Int by rememberSaveable { mutableStateOf(0) }

    Scaffold(
        topBar = {
            if (showTopAppBar) {

                TopAppBar(
                    title = { Text("Movies catalog", color = MaterialTheme.colorScheme.onBackground) },
                    actions = {
                        Row(modifier = Modifier.padding(all = 0.dp)) {
                            IconButton(onClick = { selection = 0 }) {
                                ActionIcon(vectorDrawable = ImageVector.vectorResource(id = R.drawable.ic_most_popular_svg), description = "popular", tint = getActionIconColor(selection == 0))
                            }
                            IconButton(onClick = { selection = 1 }) {
                                ActionIcon(vectorDrawable = ImageVector.vectorResource(id = R.drawable.ic_top_rated_svg), description = "top", tint = getActionIconColor(selection == 1))
                            }
                            IconButton(onClick = { selection = 2 }) {
                                ActionIcon(vectorDrawable = Icons.Outlined.FavoriteBorder, description = "fav", tint = getActionIconColor(selection == 2))
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                )
            }
        },
        content = { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Routes.MainScreen.route,
            ) {
                composable(route = Routes.MainScreen.route) {
                    MainScreen(selection = selection, modifier = Modifier.padding(innerPadding)) { movieId ->
                        navController.navigate(Routes.DetailsScreenArgsValues(movieId).route) /*{
                            navController.graph.findStartDestination().id
                        }
                        */
                    }
                }
                composable(route = Routes.DetailScreenArgsName("id").route,
                    arguments = listOf(
                        navArgument("id") {
                            type = NavType.IntType
                            nullable = false
                        },
                    )
                ) {
                    DetailScreen(movieId = it.arguments!!.getInt("id"))
                }
            }
        }
    )
}

fun getActionIconColor(guard: Boolean): Color {
    return (if (guard) Color.Black else Color.White)
}

@Composable
fun MainScreen(modifier: Modifier, selection: Int, viewModelInstance: MainViewModel = koinViewModel(), onMovieClicked: (Int) -> Unit) {

    viewModelInstance.getMovies(selection)

    //val movies:List<Movie> by viewModelInstance.movies.collectAsState()
    val movies: LazyPagingItems<Movie> = when {
        selection == 0 -> {
            viewModelInstance.moviesList.collectAsLazyPagingItems()
        }
        selection == 1 -> {
            viewModelInstance.moviesList.collectAsLazyPagingItems()
        }
        else -> {
            viewModelInstance.moviesList.collectAsLazyPagingItems()
        }
    }

    //val movies: LazyPagingItems<Movie> = viewModelInstance.moviesList.collectAsLazyPagingItems()
    Log.d("XDEBUG","selection : $selection")
    // alternativamente : val movies: LazyPagingItems<Movie> = viewModelInstance.getMovies2().collectAsLazyPagingItems()
    // se non dovessi estrarre dati paginati farei:
    // val movies: LazyPagingItems<Movie> = viewModelInstance.movies.collectAsState()

    Surface(color = MaterialTheme.colorScheme.background, modifier = modifier) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(130.dp),
        ) {
            items(count = movies.itemCount) { index ->
                movies[index]?.let {
                    MovieItemGrid(it, onMovieClicked)
                }
            }
        }

        /* example lazycolumn con paging data
        LazyColumn {
            items(count = movies.itemCount) { index ->
                val item = movies[index]
                item?.let {
                    MovieItem(item)
                }
            }
        }
         */
    }
}

@Composable
fun ActionIcon(vectorDrawable: ImageVector, description: String, tint: Color) =
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(1.dp),
    ) {
        Icon(
            imageVector = vectorDrawable,
            contentDescription = "",
            tint = tint,
            modifier = Modifier.height(24.dp)
        )
        Text(
            text = description, fontSize = 9.sp,
            modifier = Modifier
                .scale(0.9f)
                .padding(top = 0.dp)
                .offset(y = -5.dp),
            color = tint,
            fontWeight = FontWeight.Bold
            )
    }


@Composable
fun MovieItemGrid2(item: Movie, onMovieClicked: (Int) -> Unit) {
    //example uri : http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg
    val url: String = POSTER_BASE_URL + W185 + item.poster_path
    val context = LocalContext.current

    /*
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data("https://example.com/image.jpg")
            .size(Size.ORIGINAL) // Set the target size to load the image at.
            .build()
    )

    if (painter.state is AsyncImagePainter.State.Success) {
        // This will be executed during the first composition if the image is in the memory cache.
    }
     */

    val painter = remember {
        ImageRequest.Builder(context)
            .data(url)
            .size(Size.ORIGINAL)
            .crossfade(false)
            .build()
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onMovieClicked.invoke(item.id)
            }
    ) {
        AsyncImage(
            model = painter,
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.height(200.dp),
        )
    }
}

@Composable
fun MovieItemGrid(item: Movie, onMovieClicked: (Int) -> Unit) {
    //example uri : http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg
    val url: String = POSTER_BASE_URL + W185 + item.poster_path
    val context = LocalContext.current

    val model = remember {
        ImageRequest.Builder(context)
            .data(url)
            .size(Size.ORIGINAL)
            .crossfade(false)
            .build()
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onMovieClicked.invoke(item.id)
            }
    ) {
        AsyncImage(
            model = model,
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.height(200.dp)
        )
    }
}

@Composable
fun MovieItem(item: Movie) {
    //example uri : http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg
    val url: String = POSTER_BASE_URL + W185 + item.poster_path

    val model = ImageRequest.Builder(LocalContext.current)
        .data(url.replace("http", "https"))
        .size(Size.ORIGINAL)
        .crossfade(false)
        .build()
    //val myPainter = rememberAsyncImagePainter(model)

    Box(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(item.original_title)
            AsyncImage(
                model = model,
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier.height(290.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieItemPreview() {
    MyComposeM3PlayGroundTheme {
        //MovieItem()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyComposeM3PlayGroundTheme {
        //MyScaffoldWidget()
    }
}
