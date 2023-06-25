package com.example.myshoppinglist.di

import android.app.Application
import com.example.myshoppinglist.data.ShopItemRepositoryImpl
import com.example.myshoppinglist.data.database.AppDatabase
import com.example.myshoppinglist.data.database.ShopListDao
import com.example.myshoppinglist.di.annotation.ApplicationScope
import com.example.myshoppinglist.domain.ShopItemRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    fun bindShoppItemRepository(
        impl: ShopItemRepositoryImpl
    ): ShopItemRepository

    companion object {

        @Provides
        @ApplicationScope
        fun provideShopList(
            application: Application
        ): ShopListDao = AppDatabase.getInstance(application).shopListDao()
    }
}