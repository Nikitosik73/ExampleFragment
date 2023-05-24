package com.example.myshoppinglist.domain

import androidx.lifecycle.LiveData

interface ShopItemRepository {

    suspend fun addShopItem(shopItem: ShopItem)

    suspend fun editShopItem(shopItem: ShopItem)

    suspend fun removeSHopItem(shopItem: ShopItem)

    fun getShopList(): LiveData<List<ShopItem>>

    suspend fun getShopItem(shopItemId: Int): ShopItem
}