package com.example.data.model.request

import com.example.domain.model.request.AddedCartRequestModel
import kotlinx.serialization.Serializable

@Serializable
data class AddToCartRequest(
    val productId: Int,
    val price: Double,
    val quantity: Int
) {
    companion object {
        fun fromCartRequestModel(addCartRequestModel: AddedCartRequestModel) = AddToCartRequest(
            productId = addCartRequestModel.productId,
            price = addCartRequestModel.price,
            quantity = addCartRequestModel.quantity
        )
    }
}