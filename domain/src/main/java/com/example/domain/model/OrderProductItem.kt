package com.example.domain.model

data class OrderProductItem(
    val id: Int,
    val order: Int,
    val price: Double,
    val productId: Int,
    val productName: String,
    val quantity: Int,
    val userId: Int
)