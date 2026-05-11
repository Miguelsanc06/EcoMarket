package com.ecomarket.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ecomarket.app.ui.screens.*

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {

        // SPLASH
        composable("splash") {
            SplashScreen(navController)
        }

        // LOGIN
        composable("login") {
            LoginScreen(navController)
        }

        // REGISTER
        composable("register") {
            RegisterScreen(navController)
        }

        // HOME
        composable("home") {
            HomeScreen(navController)
        }

        // CATALOG
        composable("catalog") {
            CatalogScreen(navController)
        }

        // PRODUCT DETAIL
        composable("detail/{productId}") { backStackEntry ->

            val productId =
                backStackEntry.arguments?.getString("productId")?.toIntOrNull() ?: 0

            ProductDetailScreen(
                navController = navController,
                productId = productId
            )
        }

        // CART
        composable("cart") {
            CartScreen(navController)
        }

        // PAYMENT
        composable("payment") {
            PaymentScreen(navController)
        }

        // PROFILE
        composable("profile") {
            ProfileScreen(navController)
        }

        // SELLER
        composable("sellerDashboard") {
            SellerDashboardScreen(navController)
        }

        composable("orders") {
            OrdersScreen()
        }

        composable("sellerProfile") {
            SellerProfileScreen()
        }

        // ADMIN
        composable("adminDashboard") {
            AdminDashboardScreen(navController)
        }

        composable("users") {
            UsersScreen()
        }

        composable("productsManagement") {
            ProductsManagementScreen()
        }

        composable("reports") {
            ReportsScreen()
        }
    }
}