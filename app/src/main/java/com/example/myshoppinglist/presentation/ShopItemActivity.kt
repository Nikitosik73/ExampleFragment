package com.example.myshoppinglist.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.myshoppinglist.R
import com.example.myshoppinglist.databinding.ActivityShopItemBinding
import com.example.myshoppinglist.domain.ShopItem
import com.example.myshoppinglist.presentation.viewmodel.ShopItemViewModel

class ShopItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShopItemBinding
//    private lateinit var viewModel: ShopItemViewModel

    private var screenMode = MODE_UNKNOWN
    private var shopItemId = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        parseIntent()

//        addTextChangeListeners()
        launchRightMode()
//        observeViewModel()
    }

    private fun launchRightMode() {
        val fragment = when (screenMode) {
            EXTRA_ADD_MODE -> ShopItemFragment.newInstanceAddItem()
            EXTRA_EDIT_MODE -> ShopItemFragment.newInstanceEditItem(shopItemId)
            else -> throw RuntimeException("Unknown screen mode $screenMode")
        }
        supportFragmentManager.beginTransaction()
            .add(R.id.shop_item_container, fragment)
            .commit()
    }

    //
//    private fun launchEditMode() {
//        viewModel.getShopItem(shopItemId)
//        viewModel.shopItem.observe(this) {
//            binding.editName.setText(it.name)
//            binding.editCount.setText(it.count.toString())
//        }
//        binding.buttonSave.setOnClickListener {
//            viewModel.editShopItem(
//                binding.editName.text?.toString(),
//                binding.editCount.text?.toString()
//            )
//        }
//    }
//
//    private fun launchAddMode() {
//        binding.buttonSave.setOnClickListener {
//            viewModel.addShopItem(
//                binding.editName.text?.toString(),
//                binding.editCount.text?.toString()
//            )
//        }
//    }
//
//    private fun observeViewModel() {
//        viewModel.errorShopName.observe(this) {
//            val message = if (it) {
//                getString(R.string.error_input_name)
//            } else {
//                null
//            }
//            binding.tilName.error = message
//        }
//        viewModel.errorInputCount.observe(this) {
//            val message = if (it) {
//                getString(R.string.error_input_count)
//            } else {
//                null
//            }
//            binding.tilCount.error = message
//        }
//        viewModel.closeScreen.observe(this) {
//            finish()
//        }
//    }
//
//    private fun addTextChangeListeners() {
//
//        binding.editName.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                viewModel.resetInputErrorName()
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//
//            }
//        })
//        binding.editCount.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                viewModel.resetInputErrorCount()
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//
//            }
//        })
//    }
//
    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if (mode != EXTRA_ADD_MODE && mode != EXTRA_EDIT_MODE) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode
        if (screenMode == EXTRA_EDIT_MODE) {
            if (!intent.hasExtra(EXTRA_SHOP_ITEM_ID)) {
                throw RuntimeException("Param shop item id is absent")
            }
            shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
        }
    }

    companion object {

        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val EXTRA_ADD_MODE = "add_mode"
        private const val EXTRA_EDIT_MODE = "edit_mode"
        private const val MODE_UNKNOWN = ""

        fun newIntentAddMode(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, EXTRA_ADD_MODE)
            return intent
        }

        fun newIntentEditMode(context: Context, shopItemId: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, EXTRA_EDIT_MODE)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, shopItemId)
            return intent
        }
    }
}