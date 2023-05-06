package com.example.myshoppinglist.presentation.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.myshoppinglist.R

class ShopListViewHolder(itemView: View) : ViewHolder(itemView) {
    val tvName = itemView.findViewById<TextView>(R.id.textViewShopItemName)
    val tvCount = itemView.findViewById<TextView>(R.id.textViewShopItemCount)
}