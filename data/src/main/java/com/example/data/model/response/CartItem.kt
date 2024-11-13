package com.example.data.model.response

import com.example.domain.model.CartItemModel
import kotlinx.serialization.Serializable

@Serializable
data class CartItem(
    val id: Int,
    val productId: Int,
    val price: Double,
    val imageUrl: String?,
    val quantity: Int,
    val productName: String
) {
    fun toCartItemModel(): CartItemModel {
        return CartItemModel(
            id, productId,price, imageUrl, quantity, productName
        )
    }
}