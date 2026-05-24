package com.ecomarket.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
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
Pantalla perfil vendedor.
Muestra información real obtenida desde Firebase.
*/

@Composable
fun SellerProfileScreen(
    navController: NavController
) {

    val auth = FirebaseAuth.getInstance()

    val db = FirebaseFirestore.getInstance()

    val currentUser = auth.currentUser

    var name by remember {
        mutableStateOf("")
    }

    var role by remember {
        mutableStateOf("")
    }

    // Obtiene datos vendedor
    LaunchedEffect(Unit) {

        val userId =
            currentUser?.uid ?: ""

        if (userId.isNotEmpty()) {

            db.collection("users")
                .document(userId)
                .get()
                .addOnSuccessListener { document ->

                    name =
                        document.getString("name")
                            ?: ""

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
            text = "Perfil vendedor",
            style = MaterialTheme.typography.headlineMedium,
            color = GreenPrimary
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Card perfil
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
                    modifier = Modifier.size(70.dp)
                )

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                Text(
                    text = name,
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                Row {

                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = null,
                        tint = GreenPrimary
                    )

                    Spacer(
                        modifier = Modifier.width(8.dp)
                    )

                    Text(
                        text =
                            currentUser?.email ?: ""
                    )
                }

                Spacer(
                    modifier = Modifier.height(14.dp)
                )

                Text(
                    text = role
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Información adicional
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(28.dp)
        ) {

            Column(
                modifier = Modifier.padding(24.dp)
            ) {

                Text(
                    text = "Información",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(
                    modifier = Modifier.height(14.dp)
                )

                Text(
                    text =
                        "Desde este panel el vendedor " +
                                "puede administrar productos, " +
                                "visualizar órdenes y gestionar " +
                                "la información de la tienda."
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Cerrar sesión
        Button(
            onClick = {

                auth.signOut()

                navController.navigate("login") {

                    popUpTo(0)
                }
            },
            modifier = Modifier.fillMaxWidth(),
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
    }
}