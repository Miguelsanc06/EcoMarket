package com.ecomarket.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ecomarket.app.ui.theme.GreenPrimary
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun ProfileScreen(navController: NavController) {

    val auth = FirebaseAuth.getInstance()

    val db = FirebaseFirestore.getInstance()

    val currentUser = auth.currentUser

    var role by remember {
        mutableStateOf("")
    }

    // Obtiene rol real
    LaunchedEffect(Unit) {

        val userId =
            currentUser?.uid ?: ""

        if (userId.isNotEmpty()) {

            db.collection("users")
                .document(userId)
                .get()
                .addOnSuccessListener { document ->

                    role =
                        document.getString("role")
                            ?: ""
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
            text = "Mi perfil",
            style = MaterialTheme.typography.headlineMedium,
            color = GreenPrimary
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Card usuario
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(28.dp)
        ) {

            Column(
                modifier = Modifier.padding(24.dp)
            ) {

                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = GreenPrimary,
                    modifier = Modifier.size(60.dp)
                )

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                Text(
                    text = currentUser?.email ?: "",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                Text(
                    text = role
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Mis pedidos
        ProfileOption(
            title = "Mis pedidos",
            icon = Icons.Default.Email
        ) {

            navController.navigate("orders")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Configuración
        ProfileOption(
            title = "Configuración",
            icon = Icons.Default.Settings
        ) {

            navController.navigate("settings")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Cerrar sesión
        ProfileOption(
            title = "Cerrar sesión",
            icon = Icons.Default.Logout
        ) {

            auth.signOut()

            navController.navigate("login") {

                popUpTo(0)
            }
        }
    }
}

@Composable
fun ProfileOption(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {

    Card(
        onClick = {
            onClick()
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp)
    ) {

        Row(
            modifier = Modifier.padding(20.dp)
        ) {

            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = GreenPrimary
            )

            Spacer(
                modifier = Modifier.width(16.dp)
            )

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}