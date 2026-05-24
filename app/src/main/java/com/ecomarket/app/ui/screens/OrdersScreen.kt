package com.ecomarket.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ecomarket.app.models.Order
import com.ecomarket.app.ui.theme.GreenPrimary
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun OrdersScreen() {

    val db = FirebaseFirestore.getInstance()

    val auth = FirebaseAuth.getInstance()

    var orders by remember {
        mutableStateOf(listOf<Order>())
    }

    // Carga órdenes reales
    LaunchedEffect(Unit) {

        val userId =
            auth.currentUser?.uid ?: ""

        db.collection("orders")
            .document(userId)
            .collection("items")
            .addSnapshotListener { result, _ ->

                if (result != null) {

                    val orderList =
                        mutableListOf<Order>()

                    for (document in result.documents) {

                        val order = Order(
                            id = document.id,
                            name = document.getString("name") ?: "",
                            price = document.getString("price") ?: "",
                            imageUrl = document.getString("imageUrl") ?: "",
                            status = document.getString("status") ?: ""
                        )

                        orderList.add(order)
                    }

                    orders = orderList
                }
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F7FA))
            .padding(horizontal = 20.dp)
            .padding(top = 40.dp)
    ) {

        Text(
            text = "Mis pedidos",
            style = MaterialTheme.typography.headlineMedium,
            color = GreenPrimary
        )

        Spacer(modifier = Modifier.height(24.dp))

        LazyColumn {

            items(orders) { order ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    shape = RoundedCornerShape(24.dp)
                ) {

                    Row(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        AsyncImage(
                            model = order.imageUrl,
                            contentDescription = order.name,
                            modifier = Modifier.size(100.dp),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(
                            modifier = Modifier.width(16.dp)
                        )

                        Column {

                            Text(
                                text = order.name,
                                style = MaterialTheme.typography.titleMedium
                            )

                            Spacer(
                                modifier = Modifier.height(8.dp)
                            )

                            Text(
                                text = "$ ${order.price}",
                                color = GreenPrimary
                            )

                            Spacer(
                                modifier = Modifier.height(8.dp)
                            )

                            Text(
                                text = "Estado: ${order.status}"
                            )
                        }
                    }
                }
            }
        }
    }
}