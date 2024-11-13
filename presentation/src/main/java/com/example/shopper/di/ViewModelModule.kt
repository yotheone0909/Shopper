package com.example.shopper.di

import com.example.shopper.ui.feature.cart.CartViewModel
import com.example.shopper.ui.feature.home.HomeViewModel
import com.example.shopper.ui.feature.product.details.ProductDetailViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        HomeViewModel(get(), get())
    }

    viewModel {
        ProductDetailViewModel(get())
    }

    viewModel {
        CartViewModel(get(), get(), get())
    }
}