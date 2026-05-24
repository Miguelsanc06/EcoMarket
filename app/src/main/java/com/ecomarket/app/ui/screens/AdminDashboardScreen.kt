package com.ecomarket.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ecomarket.app.ui.theme.GreenPrimary
import com.google.firebase.firestore.FirebaseFirestore
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.filled.Logout
import com.google.firebase.auth.FirebaseAuth

data class AdminCard(
    val title: String,
    val value: String,
    val icon: ImageVector,
    val route: String
)

@Composable
fun AdminDashboardScreen(navController: NavController) {

    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()

    // Contadores reales
    var usersCount by remember { mutableStateOf(0) }
    var productsCount by remember { mutableStateOf(0) }

    // Carga usuarios reales
    LaunchedEffect(Unit) {

        db.collection("users")
            .get()
            .addOnSuccessListener {

                usersCount = it.size()
            }

        db.collection("products")
            .get()
            .addOnSuccessListener {

                productsCount = it.size()
            }
    }

    val cards = listOf(

        AdminCard(
            "Usuarios",
            usersCount.toString(),
            Icons.Default.People,
            "users"
        ),

        AdminCard(
            "Productos",
            productsCount.toString(),
            Icons.Default.Inventory,
            "productsManagement"
        ),

        AdminCard(
            "Reportes",
            "Activos",
            Icons.Default.Assessment,
            "reports"
        ),

        AdminCard(
            "Catálogo",
            "Ver",
            Icons.Default.Store,
            "catalog"
        )


    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F7FA))
            .padding(20.dp)
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Administrador",
            style = MaterialTheme.typography.headlineMedium,
            color = GreenPrimary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Panel de control del sistema",
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(20.dp))

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

        Spacer(modifier = Modifier.height(24.dp))

        Spacer(modifier = Modifier.height(30.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(18.dp),
            horizontalArrangement = Arrangement.spacedBy(18.dp)
        ) {

            items(cards) { card ->

                Card(
                    onClick = {
                        navController.navigate(card.route)
                    },
                    modifier = Modifier.height(170.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Transparent
                    )
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        GreenPrimary,
                                        Color(0xFF4CAF50)
                                    )
                                )
                            )
                            .padding(20.dp)
                    ) {

                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {

                            Icon(
                                imageVector = card.icon,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(34.dp)
                            )

                            Column {

                                Text(
                                    text = card.value,
                                    style = MaterialTheme.typography.headlineMedium,
                                    color = Color.White
                                )

                                Spacer(
                                    modifier = Modifier.height(4.dp)
                                )

                                Text(
                                    text = card.title,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}