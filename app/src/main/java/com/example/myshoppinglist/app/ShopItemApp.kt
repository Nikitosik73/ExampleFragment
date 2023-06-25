package com.example.myshoppinglist.app

import android.app.Application
import com.example.myshoppinglist.di.DaggerApplicationComponent

class ShopItemApp : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory()
            .create(this)
    }
}