package com.ecomarket.app.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@Composable
fun BottomBar(navController: NavController) {

    NavigationBar(
        containerColor = Color.White
    ) {

        NavigationBarItem(
            selected = false,
            onClick = {
                navController.navigate("home")
            },
            icon = {
                Icon(Icons.Default.Home, contentDescription = null)
            },
            label = {
                Text("Inicio")
            }
        )

        NavigationBarItem(
            selected = false,
            onClick = {
                navController.navigate("catalog")
            },
            icon = {
                Icon(Icons.Default.Store, contentDescription = null)
            },
            label = {
                Text("Catálogo")
            }
        )

        NavigationBarItem(
            selected = false,
            onClick = {
                navController.navigate("cart")
            },
            icon = {
                Icon(Icons.Default.ShoppingCart, contentDescription = null)
            },
            label = {
                Text("Carrito")
            }
        )

        NavigationBarItem(
            selected = false,
            onClick = {
                navController.navigate("profile")
            },
            icon = {
                Icon(Icons.Default.Person, contentDescription = null)
            },
            label = {
                Text("Perfil")
            }
        )
    }
}