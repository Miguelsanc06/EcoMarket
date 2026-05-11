package com.ecomarket.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ecomarket.app.ui.theme.GreenPrimary

@Composable
fun SellerDashboardScreen(navController: NavController) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F7FA))
            .padding(20.dp)
    ) {

        item {

            Text(
                text = "Panel del Vendedor 🛒",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = GreenPrimary
            )

            Spacer(modifier = Modifier.height(24.dp))

            DashboardCard(
                icon = Icons.Default.ShoppingCart,
                title = "Pedidos",
                value = "120",
                onClick = {
                    navController.navigate("orders")
                }
            )

            DashboardCard(
                icon = Icons.Default.Inventory,
                title = "Productos",
                value = "35",
                onClick = {
                    navController.navigate("productsManagement")
                }
            )

            DashboardCard(
                icon = Icons.Default.Person,
                title = "Perfil vendedor",
                value = "Ver",
                onClick = {
                    navController.navigate("sellerProfile")
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = GreenPrimary
                )
            ) {

                Column(
                    modifier = Modifier.padding(24.dp)
                ) {

                    Text(
                        text = "Ventas del mes",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "$ 4.500.000",
                        color = Color.White,
                        fontSize = 30.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
fun DashboardCard(
    icon: ImageVector,
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

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(22.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = GreenPrimary,
                modifier = Modifier.size(34.dp)
            )

            Spacer(modifier = Modifier.width(18.dp))

            Column {

                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = value,
                    color = Color.Gray,
                    fontSize = 16.sp
                )
            }
        }
    }
}