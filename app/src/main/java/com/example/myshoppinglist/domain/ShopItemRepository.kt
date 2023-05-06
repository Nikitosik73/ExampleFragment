package com.example.myshoppinglist.domain

import androidx.lifecycle.LiveData

interface ShopItemRepository {

    fun addShopItem(shopItem: ShopItem)

    fun editShopItem(shopItem: ShopItem)

    fun removeSHopItem(shopItem: ShopItem)

    fun getShopList(): LiveData<List<ShopItem>>

    fun getShopItem(shopItemId: Int): ShopItem
}