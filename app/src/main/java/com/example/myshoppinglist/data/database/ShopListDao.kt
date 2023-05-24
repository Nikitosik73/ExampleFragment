package com.example.myshoppinglist.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ShopListDao {

    @Query("select * from shop_items")
    fun getAllShopList(): LiveData<List<ShopItemDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addShopItem(shopItemDbModel: ShopItemDbModel)

    @Query("delete from shop_items where id=:shopItemId")
    suspend fun removeSHopItem(shopItemId: Int)

    @Query("select * from shop_items where id=:shopItemId limit 1")
    suspend fun getShopItem(shopItemId: Int): ShopItemDbModel
}