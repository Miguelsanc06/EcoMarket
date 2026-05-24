package com.ecomarket.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ecomarket.app.ui.theme.GreenPrimary
import com.google.firebase.firestore.FirebaseFirestore

/*
Pantalla de reportes administrativos.
Muestra estadísticas generales de la aplicación.
*/

@Composable
fun ReportsScreen() {

    val db = FirebaseFirestore.getInstance()

    // Total usuarios
    var totalUsers by remember {
        mutableStateOf(0)
    }

    // Total productos
    var totalProducts by remember {
        mutableStateOf(0)
    }

    // Total órdenes
    var totalOrders by remember {
        mutableStateOf(0)
    }

    // Obtiene estadísticas reales
    LaunchedEffect(Unit) {

        // Usuarios
        db.collection("users")
            .addSnapshotListener { result, _ ->

                totalUsers =
                    result?.documents?.size ?: 0
            }

        // Productos
        db.collection("products")
            .addSnapshotListener { result, _ ->

                totalProducts =
                    result?.documents?.size ?: 0
            }

        // Órdenes
        db.collectionGroup("items")
            .addSnapshotListener { result, _ ->

                totalOrders =
                    result?.documents?.size ?: 0
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
            text = "Reportes",
            style = MaterialTheme.typography.headlineMedium,
            color = GreenPrimary
        )

        Spacer(modifier = Modifier.height(30.dp))

        LazyColumn {

            item {

                ReportCard(
                    title = "Usuarios registrados",
                    value = totalUsers.toString(),
                    icon = Icons.Default.People
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                ReportCard(
                    title = "Productos registrados",
                    value = totalProducts.toString(),
                    icon = Icons.Default.Inventory
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                ReportCard(
                    title = "Órdenes realizadas",
                    value = totalOrders.toString(),
                    icon = Icons.Default.ShoppingCart
                )

                Spacer(
                    modifier = Modifier.height(24.dp)
                )

                // Información adicional
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(24.dp)
                    ) {

                        Text(
                            text = "Resumen del sistema",
                            style =
                                MaterialTheme.typography.titleMedium
                        )

                        Spacer(
                            modifier = Modifier.height(12.dp)
                        )

                        Text(
                            text =
                                "El sistema EcoMarket permite " +
                                        "la administración de productos, " +
                                        "usuarios y órdenes mediante " +
                                        "Firebase Firestore en tiempo real."
                        )
                    }
                }
            }
        }
    }
}

/*
Card reutilizable utilizada para estadísticas.
*/
@Composable
fun ReportCard(
    title: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp)
    ) {

        Row(
            modifier = Modifier.padding(24.dp)
        ) {

            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = GreenPrimary
            )

            Spacer(
                modifier = Modifier.width(16.dp)
            )

            Column {

                Text(
                    text = title,
                    style =
                        MaterialTheme.typography.titleMedium
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = value,
                    color = GreenPrimary
                )
            }
        }
    }
}