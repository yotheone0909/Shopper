package com.example.domain.model.request

data class AddedCartRequestModel(
    val productId: Int,
    val productName: String,
    val price: Double,
    val quantity: Int,
    val userId: Int
)