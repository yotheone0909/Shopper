package com.example.data.model.response

import com.example.domain.model.OrdersListModel
import kotlinx.serialization.Serializable

@Serializable
data class OrdersListResponse(
    val `data`: List<OrdersListData>,
    val msg: String
) {
    fun toDomainResponse(): OrdersListModel {
        return OrdersListModel(
            data = `data`.map { it.toDomainResponse() },
            msg = msg
        )
    }
}