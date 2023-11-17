package com.example.mycomposem3playground.domain.interactors

import com.example.mycomposem3playground.data.local.model.FavoritesItem
import com.example.mycomposem3playground.domain.model.MovieDetailInfo
import com.example.mycomposem3playground.data.repository.IRepository

class UpdateFavorites(private val repository: IRepository) {
    suspend fun execute(params: Params) {
        repository.updateFavorite(params.item, params.checked)
    }
    class Params(val item: FavoritesItem, val checked: Boolean)
}