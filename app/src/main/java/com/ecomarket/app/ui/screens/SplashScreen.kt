package com.ecomarket.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ecomarket.app.ui.theme.GreenPrimary
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay

/*
Pantalla de inicio de la aplicación.
Verifica sesión Firebase y redirige automáticamente según rol.
*/

@Composable
fun SplashScreen(navController: NavController) {

    val auth = FirebaseAuth.getInstance()

    val db = FirebaseFirestore.getInstance()

    LaunchedEffect(Unit) {

        delay(2000)

        val currentUser =
            auth.currentUser

        // Si no hay sesión
        if (currentUser == null) {

            navController.navigate("login") {

                popUpTo(0)
            }

        } else {

            // Obtiene rol usuario
            db.collection("users")
                .document(currentUser.uid)
                .get()
                .addOnSuccessListener { document ->

                    val role =
                        document.getString("role")
                            ?: ""

                    when (role) {

                        "Administrador" -> {

                            navController.navigate(
                                "adminDashboard"
                            ) {

                                popUpTo(0)
                            }
                        }

                        "Vendedor" -> {

                            navController.navigate(
                                "sellerDashboard"
                            ) {

                                popUpTo(0)
                            }
                        }

                        else -> {

                            navController.navigate(
                                "home"
                            ) {

                                popUpTo(0)
                            }
                        }
                    }
                }
        }
    }

    // Diseño splash
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        GreenPrimary,
                        Color(0xFF66BB6A)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {

            Text(
                text = "EcoMarket 🌱",
                style =
                    MaterialTheme.typography.headlineLarge,
                color = Color.White
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Text(
                text = "Compra ecológica inteligente",
                color = Color.White
            )
        }
    }
}