package com.example.myshoppinglist.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.myshoppinglist.data.ShopItemRepositoryImpl
import com.example.myshoppinglist.domain.EditShopItemUseCase
import com.example.myshoppinglist.domain.GetShopListUseCase
import com.example.myshoppinglist.domain.RemoveShopItemUseCase
import com.example.myshoppinglist.domain.ShopItem

class MainViewModel : ViewModel() {

    private val repository = ShopItemRepositoryImpl

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val removeShopItemUseCase = RemoveShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()

    fun removeShopItem(shopItem: ShopItem) {
        removeShopItemUseCase.removeShopItem(shopItem)
    }

    fun editShopItem(shopItem: ShopItem) {
        val newItem = shopItem.copy(enabled = !shopItem.enabled)
        editShopItemUseCase.editShopItem(newItem)
    }
}