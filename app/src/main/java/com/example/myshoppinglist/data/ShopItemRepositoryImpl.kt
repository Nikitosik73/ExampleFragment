package com.example.myshoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myshoppinglist.domain.ShopItem
import com.example.myshoppinglist.domain.ShopItem.Companion.UNDEFINED_ID
import com.example.myshoppinglist.domain.ShopItemRepository
import kotlin.random.Random

object ShopItemRepositoryImpl : ShopItemRepository {

    private val shopListLD = MutableLiveData<List<ShopItem>>()
    private val shopList = sortedSetOf<ShopItem>({item1, item2 -> item1.id.compareTo(item2.id)})

    private var autoIncrementId = 0

    init {
        for (i in 0..100) {
            val item = ShopItem(
                name = "Name $i",
                count = i,
                enabled = Random.nextBoolean(),
            )
            addShopItem(item)
        }
    }

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == UNDEFINED_ID) {
            shopItem.id = autoIncrementId++
        }
        shopList.add(shopItem)
        updateList()
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldElement = getShopItem(shopItem.id)
        removeSHopItem(oldElement)
        addShopItem(shopItem)
    }

    override fun removeSHopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
        updateList()
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return shopListLD
    }

    override fun getShopItem(shopItemId: Int): ShopItem {
        return shopList.find {
            it.id == shopItemId
        }?: throw RuntimeException("Element with id $shopItemId not found")
    }

    private fun updateList() {
        shopListLD.value = shopList.toList()
    }
}