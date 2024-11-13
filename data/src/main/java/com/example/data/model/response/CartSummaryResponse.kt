package com.example.data.model.response

import com.example.domain.model.CartSummary
import kotlinx.serialization.Serializable

@Serializable
data class CartSummaryResponse(
    val `data`: Summary,
    val msg: String
) {
    fun toCartSummary() = CartSummary(
        data = `data`.toSummaryData(),
        msg = msg
    )
}