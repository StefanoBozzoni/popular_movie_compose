package com.example.mycomposem3playground.domain.interactors

import com.example.domainmodule.IRepository
import com.example.mycomposem3playground.domain.model.MovieDetailInfo

class GetSingleMovieUC(private val remoteRepository: IRepository) {
    suspend fun execute(params: Params): MovieDetailInfo {
        val movie   = remoteRepository.getSingleMovie(params.id)
        val videos  = remoteRepository.getMovieVideos(params.id)
        val reviews = remoteRepository.getMovieReviews(params.id)
        val favorite = remoteRepository.getFavoriteStatus(params.id)
        return MovieDetailInfo(
            reviews.results,
            videos.results,
            movie,
            favorite
        )
    }

    class Params(val id: Int)
}