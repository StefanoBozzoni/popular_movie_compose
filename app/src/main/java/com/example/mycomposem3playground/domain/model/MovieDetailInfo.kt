package com.example.mycomposem3playground.domain.model

import com.example.domainmodule.dtos.Movie
import com.example.domainmodule.dtos.Review
import com.example.domainmodule.dtos.Video

class MovieDetailInfo(
    val review: List<Review>,
    val videos: List<Video>,
    val movie: Movie,
    var favorite: Boolean
)