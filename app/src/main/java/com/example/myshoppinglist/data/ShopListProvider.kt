package com.example.myshoppinglist.data

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log
import com.example.myshoppinglist.app.ShopItemApp
import com.example.myshoppinglist.data.database.ShopListDao
import com.example.myshoppinglist.data.mapper.ShopListMapper
import com.example.myshoppinglist.domain.ShopItem
import javax.inject.Inject

class ShopListProvider : ContentProvider() {

    private val component by lazy {
        (context as ShopItemApp).component
    }

    @Inject
    lateinit var shopDao: ShopListDao

    @Inject
    lateinit var mapper: ShopListMapper

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI("com.example.myshoppinglist", "shop_items", GET_SHOP_ITEMS_QUERY)
        addURI("com.example.myshoppinglist", "shop_items/#", GET_SHOP_ITEM_BY_ID_QUERY)
    }

    override fun onCreate(): Boolean {
        component.inject(this)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        return when(uriMatcher.match(uri)) {
            GET_SHOP_ITEMS_QUERY -> {
                return shopDao.getAllShopListCursor()
            }
            else -> null
        }
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        when(uriMatcher.match(uri)) {
            GET_SHOP_ITEMS_QUERY -> {
                values?.let {
                    val id = values.getAsInteger("id")
                    val name = values.getAsString("name")
                    val count = values.getAsInteger("count")
                    val enabled = values.getAsBoolean("enabled")
                    val item = ShopItem(
                        id = id,
                        name = name,
                        count = count,
                        enabled = enabled
                    )
                    val itemDb = mapper.mapShopItemEntityToDbModel(item)
                    shopDao.addShopItemSync(itemDb)
                }
            }
        }
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        when(uriMatcher.match(uri)) {
            GET_SHOP_ITEMS_QUERY -> {
                val itemId = selectionArgs?.get(0)?.toInt() ?: -1
                return shopDao.removeSHopItemSync(itemId)
            }
        }
        return 0
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        TODO("Not yet implemented")
    }

    companion object {

        private const val GET_SHOP_ITEMS_QUERY = 100
        private const val GET_SHOP_ITEM_BY_ID_QUERY = 101
    }
}