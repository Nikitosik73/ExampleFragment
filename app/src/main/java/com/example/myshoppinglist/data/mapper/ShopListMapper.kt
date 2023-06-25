package com.example.myshoppinglist.data.mapper

import com.example.myshoppinglist.data.database.ShopItemDbModel
import com.example.myshoppinglist.domain.ShopItem
import javax.inject.Inject

class ShopListMapper @Inject constructor() {

    fun mapShopItemEntityToDbModel(shopItem: ShopItem) = ShopItemDbModel(
        id = shopItem.id,
        name = shopItem.name,
        count = shopItem.count,
        enabled = shopItem.enabled
    )

    fun mapShopItemDbModelToEntity(shopItemDbModel: ShopItemDbModel) = ShopItem(
        id = shopItemDbModel.id,
        name = shopItemDbModel.name,
        count = shopItemDbModel.count,
        enabled = shopItemDbModel.enabled
    )

    fun mapShopListDbModelToEntity(list: List<ShopItemDbModel>) = list.map {
        mapShopItemDbModelToEntity(it)
    }
}