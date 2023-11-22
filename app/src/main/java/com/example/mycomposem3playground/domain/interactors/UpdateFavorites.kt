package com.example.mycomposem3playground.domain.interactors

import com.example.domainmodule.IRepository
import com.example.domainmodule.model.FavoritesItem

class UpdateFavorites(private val repository: IRepository) {
    suspend fun execute(params: Params) {
        repository.updateFavorite(params.item, params.checked)
    }
    class Params(val item: FavoritesItem, val checked: Boolean)
}