package com.example.myshoppinglist.di

import android.app.Application
import com.example.myshoppinglist.data.ShopListProvider
import com.example.myshoppinglist.di.annotation.ApplicationScope
import com.example.myshoppinglist.presentation.MainActivity
import com.example.myshoppinglist.presentation.ShopItemFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {

    fun inject(activity: MainActivity)

    fun inject(fragment: ShopItemFragment)

    fun inject(provider: ShopListProvider)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}