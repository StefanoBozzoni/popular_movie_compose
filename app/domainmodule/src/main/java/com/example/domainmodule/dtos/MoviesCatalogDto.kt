package com.example.domainmodule.dtos

data class MoviesCatalogDto(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)