package com.example.mycomposem3playground.data.remote

import com.example.mycomposem3playground.data.remote.dtos.Movie
import com.example.mycomposem3playground.data.remote.dtos.MoviesCatalogDto
import com.example.mycomposem3playground.data.remote.dtos.ReviewsCatalog
import com.example.mycomposem3playground.data.remote.dtos.VideoCatalog
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
    suspend fun getReviews(@Path("id") id: Int) : Response<ReviewsCatalog>

    @GET("/3/movie/{id}/videos")
    suspend fun getVideos(@Path("id") id: Int): Response<VideoCatalog>

    @GET("/3/movie/{id}")
    suspend fun getSingleMovie(@Path("id") id: Int): Response<Movie>

}