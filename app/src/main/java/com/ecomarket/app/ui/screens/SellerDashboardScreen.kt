package com.ecomarket.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ecomarket.app.ui.theme.GreenPrimary
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

/*
Panel principal del vendedor.
Muestra estadísticas y accesos rápidos.
*/

@Composable
fun SellerDashboardScreen(
    navController: NavController
) {

    val auth = FirebaseAuth.getInstance()

    val db = FirebaseFirestore.getInstance()

    // Cantidad productos
    var totalProducts by remember {
        mutableStateOf(0)
    }

    // Cantidad órdenes
    var totalOrders by remember {
        mutableStateOf(0)
    }

    // Obtiene productos reales
    LaunchedEffect(Unit) {

        db.collection("products")
            .addSnapshotListener { result, _ ->

                totalProducts =
                    result?.documents?.size ?: 0
            }

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
            text = "Panel vendedor",
            style = MaterialTheme.typography.headlineMedium,
            color = GreenPrimary
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Cerrar sesión
        Button(
            onClick = {

                auth.signOut()

                navController.navigate("login") {

                    popUpTo(0)
                }
            },
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red
            )
        ) {

            Icon(
                imageVector = Icons.Default.Logout,
                contentDescription = null
            )

            Spacer(
                modifier = Modifier.width(8.dp)
            )

            Text("Cerrar sesión")
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Estadísticas
        LazyColumn {

            item {

                DashboardCard(
                    title = "Productos",
                    value = totalProducts.toString(),
                    icon = Icons.Default.Inventory
                ) {

                    navController.navigate(
                        "productsManagement"
                    )
                }

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                DashboardCard(
                    title = "Órdenes",
                    value = totalOrders.toString(),
                    icon = Icons.Default.ShoppingCart
                ) {

                    navController.navigate(
                        "orders"
                    )
                }

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                DashboardCard(
                    title = "Perfil vendedor",
                    value = "Ver perfil",
                    icon = Icons.Default.Person
                ) {

                    navController.navigate(
                        "sellerProfile"
                    )
                }
            }
        }
    }
}

/*
Card reutilizable utilizada en dashboards.
*/
@Composable
fun DashboardCard(
    title: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
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