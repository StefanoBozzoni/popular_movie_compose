package com.example.datamodule.data.dtos

data class ReviewsCatalogDto(
    val id: Int,
    val page: Int,
    val results: List<ReviewDto>,
    val total_pages: Int,
    val total_results: Int
)