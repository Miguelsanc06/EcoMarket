package com.ecomarket.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ecomarket.app.ui.theme.GreenPrimary

data class Order(
    val id: String,
    val customer: String,
    val total: String,
    val status: String
)

@Composable
fun OrdersScreen() {

    val orders = listOf(

        Order(
            "001",
            "Carlos Pérez",
            "$120.000",
            "Entregado"
        ),

        Order(
            "002",
            "Laura Gómez",
            "$85.000",
            "En camino"
        ),

        Order(
            "003",
            "Andrés Ruiz",
            "$230.000",
            "Pendiente"
        ),

        Order(
            "004",
            "Sofía Martínez",
            "$60.000",
            "Entregado"
        )
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F7FA))
            .padding(20.dp)
    ) {

        item {
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Mis Pedidos 📦",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = GreenPrimary
            )

            Spacer(modifier = Modifier.height(24.dp))
        }

        items(orders) { order ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    Text(
                        text = "Pedido #${order.id}",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Cliente: ${order.customer}"
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Total: ${order.total}"
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Estado: ${order.status}",
                        color = when (order.status) {

                            "Entregado" -> Color(0xFF4CAF50)

                            "En camino" -> Color(0xFFFF9800)

                            else -> Color.Red
                        },
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}