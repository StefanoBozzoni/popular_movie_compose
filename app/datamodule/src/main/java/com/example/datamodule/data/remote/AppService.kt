package com.example.datamodule.data.remote

import com.example.datamodule.data.dtos.MovieDto
import com.example.domainmodule.model.Movie
import com.example.datamodule.data.dtos.MoviesCatalogDto
import com.example.datamodule.data.dtos.ReviewsCatalogDto
import com.example.datamodule.data.dtos.VideoCatalogDto
import com.example.domainmodule.model.ReviewsCatalog
import com.example.domainmodule.model.VideoCatalog
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AppService {
    @GET("/3/movie/popular")  //"https://api.themoviedb.org/3/movie/popular"
    suspend fun getMovies(@Query("page") pageNum: Int? = null): Response<MoviesCatalogDto>

    @GET("/3/movie/top_rated")  //"https://api.themoviedb.org/3/movie/popular"
    suspend fun getTopRatedMovies(@Query("page") pageNum: Int? = null): Response<MoviesCatalogDto>

    @GET("/3/movie/{id}/reviews")
    suspend fun getReviews(@Path("id") id: Int) : Response<ReviewsCatalogDto>

    @GET("/3/movie/{id}/videos")
    suspend fun getVideos(@Path("id") id: Int): Response<VideoCatalogDto>

    @GET("/3/movie/{id}")
    suspend fun getSingleMovie(@Path("id") id: Int): Response<MovieDto>

}