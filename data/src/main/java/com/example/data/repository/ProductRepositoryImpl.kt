package com.example.data.repository

import com.example.domain.model.Product
import com.example.domain.model.ProductListModel
import com.example.domain.network.NetworkService
import com.example.domain.network.ResultWrapper
import com.example.domain.repository.ProductRepository

class ProductRepositoryImpl(private val networkService: NetworkService): ProductRepository {
    override suspend fun getProduct(category: Int?): ResultWrapper<ProductListModel> {
        return networkService.getProducts(category)
    }
}