package com.example.mycomposem3playground.domain.model

import com.example.mycomposem3playground.data.remote.dtos.*

class MovieDetailInfo(
    val review: List<Review>,
    val videos: List<Video>,
    val movie: Movie,
    var favorite: Boolean
)