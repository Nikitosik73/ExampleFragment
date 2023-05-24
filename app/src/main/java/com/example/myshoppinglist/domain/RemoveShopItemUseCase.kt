package com.example.myshoppinglist.domain

class RemoveShopItemUseCase(private val shopItemRepository: ShopItemRepository) {

    suspend fun removeShopItem(shopItem: ShopItem) {
        shopItemRepository.removeSHopItem(shopItem)
    }
}