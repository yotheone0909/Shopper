package com.example.data.repository

import com.example.domain.model.AddressDomainModel
import com.example.domain.network.NetworkService
import com.example.domain.network.ResultWrapper
import com.example.domain.repository.OrderRepository

class OrderRepositoryImpl(private val networkService: NetworkService) : OrderRepository {
    override suspend fun placeOrder(addressDomainModel: AddressDomainModel): ResultWrapper<Long> {
        return networkService.placeOrder(addressDomainModel, 1)
    }
}