package com.example.myshoppinglist.domain

class RemoveShopItemUseCase(private val shopItemRepository: ShopItemRepository) {

    fun removeShopItem(shopItem: ShopItem) {
        shopItemRepository.removeSHopItem(shopItem)
    }
}