package com.example.shopper.navigation

import com.example.domain.model.Product
import com.example.shopper.model.UiProductModel
import com.example.shopper.model.UserAddress
import kotlinx.serialization.Serializable

@Serializable
object HomeScreen

@Serializable
object CartScreen

@Serializable
object ProfileScreen

@Serializable
object CartSummaryScreen

@Serializable
data class ProductDetails(val product: UiProductModel)

@Serializable
data class UserAddressRoute(val userAddressRouteWrapper: UserAddressRouteWrapper)