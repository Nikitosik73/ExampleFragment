package com.example.myshoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.example.myshoppinglist.data.database.ShopListDao
import com.example.myshoppinglist.data.mapper.ShopListMapper
import com.example.myshoppinglist.domain.ShopItem
import com.example.myshoppinglist.domain.ShopItemRepository
import javax.inject.Inject

class ShopItemRepositoryImpl @Inject constructor(
    private val shopListDao: ShopListDao,
    private val mapper: ShopListMapper
) : ShopItemRepository {

    override suspend fun addShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(mapper.mapShopItemEntityToDbModel(shopItem))
    }

    override suspend fun editShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(mapper.mapShopItemEntityToDbModel(shopItem))
    }

    override suspend fun removeSHopItem(shopItem: ShopItem) {
        shopListDao.removeSHopItem(shopItem.id)
    }

    override suspend fun getShopItem(shopItemId: Int): ShopItem {
        val dbModel = shopListDao.getShopItem(shopItemId)
        return mapper.mapShopItemDbModelToEntity(dbModel)
    }

    // первый способ преоборазования одной liveData в другую
//    override fun getShopList(): LiveData<List<ShopItem>> = MediatorLiveData<List<ShopItem>>().apply {
//        addSource(shopListDao.getAllShopList()) { shopListDbModel ->
//            value = mapper.mapShopListDbModelToEntity(shopListDbModel)
//        }
//    }
    // второй способ
    override fun getShopList(): LiveData<List<ShopItem>> {
        val allShopList = shopListDao.getAllShopList()
        return allShopList.switchMap { shopItemDbModels ->
            MutableLiveData<List<ShopItem>>().apply {
                value = mapper.mapShopListDbModelToEntity(shopItemDbModels)
            }
        }
    }
}