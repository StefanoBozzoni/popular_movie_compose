package com.example.datamodule.data.dtos

data class MoviesCatalogDto(
    val page: Int,
    val results: List<MovieDto>,
    val total_pages: Int,
    val total_results: Int
)