package com.example.myshoppinglist.data.database

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ShopListDao {

    @Query("select * from shop_items")
    fun getAllShopList(): LiveData<List<ShopItemDbModel>>

    @Query("select * from shop_items")
    fun getAllShopListCursor(): Cursor

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addShopItem(shopItemDbModel: ShopItemDbModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addShopItemSync(shopItemDbModel: ShopItemDbModel)

    @Query("delete from shop_items where id=:shopItemId")
    suspend fun removeSHopItem(shopItemId: Int)

    @Query("delete from shop_items where id=:shopItemId")
    fun removeSHopItemSync(shopItemId: Int): Int

    @Query("select * from shop_items where id=:shopItemId limit 1")
    suspend fun getShopItem(shopItemId: Int): ShopItemDbModel

    @Query("select * from shop_items where id=:shopItemId limit 1")
    fun getShopItemSync(shopItemId: Int): ShopItemDbModel
}