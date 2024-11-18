package com.example.shopper.ui.feature.orders

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.domain.model.OrdersData
import com.example.domain.model.OrdersListModel
import com.example.shopper.navigation.OrdersScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun OrdersScreen(viewModel: OrdersViewModel = koinViewModel()) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Orders",
                modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.titleMedium
            )
        }

        //Tab Row
        val tabs = listOf("All", "Pending", "Delivered", "Cancelled")
        val selectedTab = remember {
            mutableIntStateOf(0)
        }
        TabRow(selectedTabIndex = selectedTab.intValue) {
            tabs.forEach {
                Text(
                    text = it, modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        when (selectedTab.intValue) {
            0 -> {
                OrdersList(listOf())
            }

            1 -> {
                OrdersList(listOf())
            }

            2 -> {
                OrdersList(listOf())
            }

            3 -> {
                OrdersList(listOf())
            }
        }
    }
}


@Composable
fun OrdersList(orders: List<OrdersData>) {
    LazyColumn {
        items(orders,
            key = { order -> order.id }) {
            OrderItem(order = it)
        }
    }
}

@Composable
fun OrderItem(order: OrdersData) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                Color.LightGray.copy(alpha = 0.4f)
            )
            .padding(8.dp)
    ) {
        Text(text = "Order Id: ${order.id}")
        Text(text = "Order Date: ${order.orderDate}")
        Text(text = "Total Amount: ${order.totalAmount}")
        Text(text = "Status: ${order.status}")
    }
}