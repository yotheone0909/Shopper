package com.example.shopper.di

import com.example.shopper.ui.feature.account.login.LoginViewModel
import com.example.shopper.ui.feature.account.register.RegisterViewModel
import com.example.shopper.ui.feature.cart.CartViewModel
import com.example.shopper.ui.feature.home.HomeViewModel
import com.example.shopper.ui.feature.orders.OrdersViewModel
import com.example.shopper.ui.feature.product.details.ProductDetailViewModel
import com.example.shopper.ui.feature.summary.CartSummaryViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        HomeViewModel(get(), get())
    }

    viewModel {
        ProductDetailViewModel(get(), get())
    }

    viewModel {
        CartViewModel(get(), get(), get(), get())
    }

    viewModel {
        CartSummaryViewModel(get(), get(), get())
    }

    viewModel {
        OrdersViewModel(get(), get())
    }

    viewModel {
        LoginViewModel(get(), get())
    }

    viewModel {
        RegisterViewModel(get(), get())
    }
}