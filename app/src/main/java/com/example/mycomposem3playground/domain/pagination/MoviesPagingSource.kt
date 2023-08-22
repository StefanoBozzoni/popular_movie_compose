package com.example.mycomposem3playground.domain.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mycomposem3playground.data.remote.AppService
import com.example.mycomposem3playground.data.remote.dtos.Movie
import com.example.mycomposem3playground.domain.repository.IRepository

class MoviesPagingSource(
    private val repository: AppService,
    private val selection: Int
): PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: 1
            val movieList = when (selection) {
                0 -> repository.getMovies(pageNum = page).body()?.results ?: emptyList()
                1 -> repository.getTopRatedMovies(pageNum = page).body()?.results ?: emptyList()
                else -> repository.getTopRatedMovies(pageNum = page).body()?.results ?: emptyList()
            }


            LoadResult.Page(
                data = movieList,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (movieList.isEmpty()) null else page.plus(1),
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}