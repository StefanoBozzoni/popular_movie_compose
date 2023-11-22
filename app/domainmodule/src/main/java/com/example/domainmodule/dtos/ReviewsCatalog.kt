package com.example.domainmodule.dtos

data class ReviewsCatalog(
    val id: Int,
    val page: Int,
    val results: List<Review>,
    val total_pages: Int,
    val total_results: Int
)