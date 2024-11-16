package com.example.shopper.navigation

import android.os.Parcelable
import com.example.shopper.model.UserAddress
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class UserAddressRouteWrapper(
    val userAddress: UserAddress?
) : Parcelable
