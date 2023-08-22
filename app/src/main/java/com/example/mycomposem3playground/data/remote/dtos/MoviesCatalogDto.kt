package com.example.mycomposem3playground.data.remote.dtos

data class MoviesCatalogDto(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)