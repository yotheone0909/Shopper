package com.example.shopper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.shopper.model.UiProductModel
import com.example.shopper.navigation.CartScreen
import com.example.shopper.navigation.CartSummaryScreen
import com.example.shopper.navigation.HomeScreen
import com.example.shopper.navigation.LoginScreen
import com.example.shopper.navigation.OrdersScreen
import com.example.shopper.navigation.ProductDetails
import com.example.shopper.navigation.ProfileScreen
import com.example.shopper.navigation.RegisterScreen
import com.example.shopper.navigation.UserAddressRoute
import com.example.shopper.navigation.UserAddressRouteWrapper
import com.example.shopper.navigation.productNavType
import com.example.shopper.navigation.userAddressNavType
import com.example.shopper.ui.feature.account.login.LoginScreen
import com.example.shopper.ui.feature.account.register.RegisterScreen
import com.example.shopper.ui.feature.cart.CartScreen
import com.example.shopper.ui.feature.home.HomeScreen
import com.example.shopper.ui.feature.orders.OrdersScreen
import com.example.shopper.ui.feature.product.details.ProductDetailsScreen
import com.example.shopper.ui.feature.summary.CartSummaryScreen
import com.example.shopper.ui.feature.user_address.UserAddressScreen
import com.example.shopper.ui.theme.ShopperTheme
import kotlin.reflect.typeOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShopperTheme {
                val shouldShowBottomNav = remember {
                    mutableStateOf(true)
                }
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        AnimatedVisibility(
                            visible = shouldShowBottomNav.value,
                            enter = fadeIn()
                        ) {
                            BottomNavigationBar(navController)
                        }
                    }) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = if (ShopperSession.getUser() != null) {
                                HomeScreen
                            } else {
                                LoginScreen
                            }
                        ) {
                            composable<LoginScreen> {
                                shouldShowBottomNav.value = false
                                LoginScreen(navController)
                            }
                            composable<RegisterScreen> {
                                shouldShowBottomNav.value = false
                                RegisterScreen(navController)
                            }
                            composable<HomeScreen> {
                                HomeScreen(navController)
                                shouldShowBottomNav.value = true
                            }
                            composable<CartScreen> {
                                shouldShowBottomNav.value = true
                                CartScreen(navController)
                            }
                            composable<OrdersScreen> {
                                shouldShowBottomNav.value = true
                                OrdersScreen()
                            }
                            composable<ProfileScreen> {
                                Box(modifier = Modifier.fillMaxWidth()) {
                                    Text("Profile")
                                }
                                shouldShowBottomNav.value = true
                            }
                            composable<CartSummaryScreen> {
                                shouldShowBottomNav.value = false
                                CartSummaryScreen(navController = navController)
                            }
                            composable<ProductDetails>(
                                typeMap = mapOf(typeOf<UiProductModel>() to productNavType)
                            ) {
                                val productRoute = it.toRoute<ProductDetails>()
                                ProductDetailsScreen(navController, productRoute.product)
                                shouldShowBottomNav.value = false
                            }
                            composable<UserAddressRoute>(
                                typeMap = mapOf(typeOf<UserAddressRouteWrapper>() to userAddressNavType)
                            ) {
                                shouldShowBottomNav.value = false
                                val userAddressRoute = it.toRoute<UserAddressRoute>()
                                UserAddressScreen(
                                    navController,
                                    userAddressRoute.userAddressRouteWrapper?.userAddress
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {

    NavigationBar {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        val items = listOf(
            BottomNavItems.Home,
            BottomNavItems.Orders,
            BottomNavItems.Profile
        )

        items.forEach { item ->
            val isSelected = currentRoute?.substringBefore("?") == item.route::class.qualifiedName
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { startRoute ->
                            popUpTo(startRoute) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                label = {
                    Text(text = item.title)
                },
                icon = {
                    Image(
                        painter = painterResource(id = item.icon), contentDescription = null,
                        colorFilter = ColorFilter.tint(if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray)
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray
                )
            )
        }
    }
}

sealed class BottomNavItems(val route: Any, val title: String, val icon: Int) {
    data object Home :
        BottomNavItems(HomeScreen, "Home", R.drawable.ic_home)

    data object Orders : BottomNavItems(OrdersScreen, "Cart", R.drawable.ic_orders)
    data object Profile : BottomNavItems(ProfileScreen, "Profile", R.drawable.ic_profile_bn)
}