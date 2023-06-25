package com.example.myshoppinglist.domain

import javax.inject.Inject

class RemoveShopItemUseCase @Inject constructor(
    private val shopItemRepository: ShopItemRepository
) {

    suspend fun removeShopItem(shopItem: ShopItem) {
        shopItemRepository.removeSHopItem(shopItem)
    }
}