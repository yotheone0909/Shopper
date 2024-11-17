package com.example.data.model.request

import com.example.domain.model.AddressDomainModel
import kotlinx.serialization.Serializable

@Serializable
data class AddressDataModel(
    val addressLine: String,
    val city: String,
    val state: String,
    val postalCode: String,
    val country: String
) {
    companion object {
        fun fromDomainAddress(userAddress: AddressDomainModel) = AddressDataModel(
            addressLine = userAddress.addressLine,
            city = userAddress.city,
            state = userAddress.state,
            postalCode = userAddress.postalCode,
            country = userAddress.country
        )
    }
}
