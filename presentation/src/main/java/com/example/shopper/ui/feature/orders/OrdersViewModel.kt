package com.example.shopper.ui.feature.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.OrdersData
import com.example.domain.network.ResultWrapper
import com.example.domain.usecase.OrderListUseCase
import com.example.shopper.ShopperSession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OrdersViewModel(
    private val orderListUseCase: OrderListUseCase,
    private val shopperSession: ShopperSession
) : ViewModel() {

    private val _ordersEvent = MutableStateFlow<OrdersEvent>(OrdersEvent.Loading)
    val ordersEvent = _ordersEvent.asStateFlow()
    private val userDomainModel = shopperSession.getUser()

    init {
        getOrderList()
    }

    fun filterOrder(list: List<OrdersData>, filter: String): List<OrdersData> {
        val filteredList = list.filter { it.status == filter }
        return filteredList
    }

    private fun getOrderList() {
        viewModelScope.launch {
            when(val result = orderListUseCase.execute(userDomainModel!!.id!!.toLong())) {
                is ResultWrapper.Failure -> {
                    _ordersEvent.value = OrdersEvent.Error("Something went wrong!")
                }
                is ResultWrapper.Success -> {
                    _ordersEvent.value = OrdersEvent.Success(result.value.data)
                }
            }
        }
    }

}

sealed class OrdersEvent {
    data object Loading : OrdersEvent()
    data class Success(val data: List<OrdersData>) : OrdersEvent()
    data class Error(val errorMsg: String) : OrdersEvent()

}