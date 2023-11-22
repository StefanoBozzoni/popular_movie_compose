package com.example.domainmodule.model

class MovieDetailInfo(
    val review: List<Review>,
    val videos: List<Video>,
    val movie: Movie,
    var favorite: Boolean
)