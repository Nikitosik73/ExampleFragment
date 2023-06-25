package com.example.myshoppinglist.di

import androidx.lifecycle.ViewModel
import com.example.myshoppinglist.di.annotation.ViewModelKey
import com.example.myshoppinglist.presentation.viewmodel.MainViewModel
import com.example.myshoppinglist.presentation.viewmodel.ShopItemViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(
        impl: MainViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ShopItemViewModel::class)
    fun bindShopItemViewModel(
        impl: ShopItemViewModel
    ): ViewModel
}