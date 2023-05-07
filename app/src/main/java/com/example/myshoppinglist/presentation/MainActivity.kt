package com.example.myshoppinglist.presentation

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.myshoppinglist.R
import com.example.myshoppinglist.databinding.ActivityMainBinding
import com.example.myshoppinglist.presentation.adapter.ShopListAdapter
import com.example.myshoppinglist.presentation.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var shopAdapter: ShopListAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.shopList.observe(this) {
            Log.d("test_of_load_data", it.toString())
            shopAdapter.submitList(it)
        }
        binding.addShopItemButton.setOnClickListener {
            if (isOnePaneMode()) {
                val intent = ShopItemActivity.newIntentAddMode(this@MainActivity)
                startActivity(intent)
            } else {
                launchScreenMode(ShopItemFragment.newInstanceAddItem())
            }
        }
    }

    private fun launchScreenMode(fragment: Fragment) {
        supportFragmentManager.popBackStack() // метод который удалит фрагмент из стека
        supportFragmentManager.beginTransaction()
            .replace(R.id.shop_item_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun isOnePaneMode(): Boolean {
        return binding.shopItemContainer == null
    }

    private fun setupRecyclerView() {
        shopAdapter = ShopListAdapter()
        with(binding) {
            with(recyclerViewShopItem) {
                adapter = shopAdapter
                recycledViewPool.setMaxRecycledViews(
                    ShopListAdapter.VIEW_TYPE_ENABLED, ShopListAdapter.MAX_POOL_SIZE
                )
                recycledViewPool.setMaxRecycledViews(
                    ShopListAdapter.VIEW_TYPE_DISABLED, ShopListAdapter.MAX_POOL_SIZE
                )
            }
        }
        setupOnLongClick()
        setupOnClick()
        removeToSwipe()
    }

    private fun removeToSwipe() {

        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val item = shopAdapter.currentList[position]
                viewModel.removeShopItem(item)
            }
        }
        ItemTouchHelper(callback).apply {
            attachToRecyclerView(binding.recyclerViewShopItem)
        }
    }

    private fun setupOnLongClick() {
        shopAdapter.onShopItemLongClickListener = {
            viewModel.editShopItem(it)
        }
    }

    private fun setupOnClick() {
        shopAdapter.onShopItemClickListener = {
            Log.d("test_click_listener", it.toString())
            if (isOnePaneMode()) {
                val intent = ShopItemActivity.newIntentEditMode(
                    this@MainActivity,
                    it.id
                )
                startActivity(intent)
            } else {
                launchScreenMode(ShopItemFragment.newInstanceEditItem(it.id))
            }
        }
    }
}