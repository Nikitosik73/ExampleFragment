package com.example.myshoppinglist.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.example.myshoppinglist.R
import com.example.myshoppinglist.databinding.ShopItemDisabledBinding
import com.example.myshoppinglist.databinding.ShopItemEnabledBinding
import com.example.myshoppinglist.domain.ShopItem
import com.example.myshoppinglist.presentation.callback.ShopItemDiffCallBack
import com.example.myshoppinglist.presentation.viewholder.ShopListViewHolder

class ShopListAdapter : ListAdapter<ShopItem, ShopListViewHolder>(
    ShopItemDiffCallBack()
) {

    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null

    private var count = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopListViewHolder {
//        Log.d("ShopListAdapter", "onCreateViewHolder count: ${++count}")
        val layout = when(viewType) {
            VIEW_TYPE_ENABLED -> R.layout.shop_item_enabled
            VIEW_TYPE_DISABLED -> R.layout.shop_item_disabled
            else -> throw RuntimeException("Unknown view type $viewType")
        }
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layout,
            parent,
            false
        )
        return ShopListViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ShopListViewHolder, position: Int) {
//        Log.d("ShopListAdapter", "onCreateViewHolder count: ${++count}")
        val shopItemAd = getItem(position)
        val binding = holder.binding
        with(binding) {
            when (this) {
                is ShopItemDisabledBinding -> {
                    shopItem = shopItemAd
                }
                is ShopItemEnabledBinding -> {
                    shopItem = shopItemAd
                }
            }

            root.setOnLongClickListener {
                onShopItemLongClickListener?.invoke(shopItemAd)
                true
            }
            root.setOnClickListener {
                onShopItemClickListener?.invoke(shopItemAd)
            }
        }
    }

    companion object {
        const val VIEW_TYPE_ENABLED = 0
        const val VIEW_TYPE_DISABLED = 1

        const val MAX_POOL_SIZE = 18
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item.enabled) {
            VIEW_TYPE_ENABLED
        } else {
            VIEW_TYPE_DISABLED
        }
    }
}