package com.example.shopper.di

import com.example.shopper.ShopperSession
import org.koin.dsl.module

val presentationModule = module {
    includes(viewModelModule)
    single { ShopperSession(get()) }
}