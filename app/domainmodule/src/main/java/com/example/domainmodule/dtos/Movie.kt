package com.example.domainmodule.dtos

data class Movie(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)

val emptyMovie : Movie = Movie(
    adult = false,
    backdrop_path = "",
    genre_ids = emptyList(),
    id = 0,
    original_language = "",
    original_title = "",
    overview = "",
    popularity = 0.0,
    poster_path = "",
    release_date = "",
    title = "",
    video = false,
    vote_average = 0.0,
    vote_count = 0
)