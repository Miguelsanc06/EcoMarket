package com.ecomarket.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ecomarket.app.ui.theme.GreenPrimary

@Composable
fun AdminDashboardScreen(navController: NavController) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F7FA))
            .padding(20.dp)
    ) {

        item {

            Text(
                text = "Panel Administrador 👑",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = GreenPrimary
            )

            Spacer(modifier = Modifier.height(24.dp))

            AdminCard("Usuarios", "125", {
                navController.navigate("users")
            })

            AdminCard("Productos", "87", {
                navController.navigate("productsManagement")
            })

            AdminCard("Reportes", "12", {
                navController.navigate("reports")
            })
        }
    }
}

@Composable
fun AdminCard(
    title: String,
    value: String,
    onClick: () -> Unit
) {

    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Column(
            modifier = Modifier.padding(22.dp)
        ) {

            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = value,
                color = GreenPrimary,
                fontSize = 26.sp
            )
        }
    }
}