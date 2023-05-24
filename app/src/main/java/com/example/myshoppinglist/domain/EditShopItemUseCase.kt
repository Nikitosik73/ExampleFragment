package com.example.myshoppinglist.domain

class EditShopItemUseCase(private val shopItemRepository: ShopItemRepository) {

    suspend fun editShopItem(shopItem: ShopItem) {
        shopItemRepository.editShopItem(shopItem)
    }
}