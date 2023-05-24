package com.example.myshoppinglist.data

import android.app.Application
import androidx.lifecycle.*
import com.example.myshoppinglist.data.database.AppDatabase
import com.example.myshoppinglist.data.mapper.ShopListMapper
import com.example.myshoppinglist.domain.ShopItem
import com.example.myshoppinglist.domain.ShopItem.Companion.UNDEFINED_ID
import com.example.myshoppinglist.domain.ShopItemRepository
import kotlin.random.Random

class ShopItemRepositoryImpl(
    application: Application
) : ShopItemRepository {

    private val shopListDao = AppDatabase.getInstance(application).shopListDao()
    private val mapper = ShopListMapper()

    override fun addShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(mapper.mapShopItemEntityToDbModel(shopItem))
    }

    override fun editShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(mapper.mapShopItemEntityToDbModel(shopItem))
    }

    override fun removeSHopItem(shopItem: ShopItem) {
        shopListDao.removeSHopItem(shopItem.id)
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

    override fun getShopItem(shopItemId: Int): ShopItem {
        val dbModel = shopListDao.getShopItem(shopItemId)
        return mapper.mapShopItemDbModelToEntity(dbModel)
    }
}