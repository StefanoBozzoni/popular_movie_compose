package com.example.mycomposem3playground

sealed class Routes(val route: String) {
    object MainScreen: Routes("MainScreen")
    object DetailScreen: Routes("DetailScreen")
    class  DetailScreenArgsName(arg1: String): Routes("${DetailScreen.route}/{$arg1}")
    class  DetailsScreenArgsValues(id: Int): Routes("${DetailScreen.route}/$id")
}