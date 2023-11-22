package com.example.datamodule.data.mapper

import com.example.datamodule.data.dtos.AuthorDetailsDto
import com.example.datamodule.data.dtos.MovieDto
import com.example.datamodule.data.dtos.ReviewDto
import com.example.datamodule.data.dtos.ReviewsCatalogDto
import com.example.datamodule.data.dtos.VideoCatalogDto
import com.example.datamodule.data.dtos.VideoDto
import com.example.domainmodule.model.AuthorDetails
import com.example.domainmodule.model.Movie
import com.example.domainmodule.model.Review
import com.example.domainmodule.model.ReviewsCatalog
import com.example.domainmodule.model.Video
import com.example.domainmodule.model.VideoCatalog

fun MovieDto.toDomain(): Movie  =
    Movie(
        adult = adult,
        backdrop_path = backdrop_path,
        genre_ids = genre_ids,
        id = id,
        original_language = original_language,
        original_title = original_title,
        overview = overview,
        popularity = popularity,
        poster_path = poster_path,
        release_date = release_date,
        title = title,
        video = video,
        vote_average = vote_average,
        vote_count = vote_count
    )

fun VideoCatalogDto.toDomain(): VideoCatalog =
    VideoCatalog(
        id = id, results = results.map { it.toDomain() }
    )

fun VideoDto.toDomain(): Video =
    Video(
        id = id,
        iso_3166_1 = iso_3166_1,
        iso_639_1 = iso_639_1,
        key = key,
        name = name,
        official = official,
        published_at = published_at,
        site = site,
        size = size,
        type = type
    )

fun ReviewsCatalogDto.toDomain(): ReviewsCatalog =
    ReviewsCatalog(
        id = id,
        page = page,
        results = results.map { it.toDomain() },
        total_pages = total_pages,
        total_results = total_results
    )

fun ReviewDto.toDomain(): Review =
    Review(
        author = author,
        author_details = author_details.toDomain(),
        content = content,
        created_at = created_at,
        id = id,
        updated_at = updated_at,
        url = url
    )

fun AuthorDetailsDto.toDomain(): AuthorDetails =
    AuthorDetails(
        avatar_path = avatar_path,
        name = name,
        rating = rating,
        username = username

    )

