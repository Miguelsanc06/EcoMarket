package com.ecomarket.app.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Save
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
fun SettingsScreen(navController: NavController) {

    val auth = FirebaseAuth.getInstance()

    val db = FirebaseFirestore.getInstance()

    val currentUser = auth.currentUser

    var name by remember {
        mutableStateOf("")
    }

    var role by remember {
        mutableStateOf("")
    }

    // Carga datos usuario
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

        // Botón volver
        IconButton(
            onClick = {

                navController.popBackStack()
            }
        ) {

            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                tint = GreenPrimary
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Título
        Text(
            text = "Configuración",
            style = MaterialTheme.typography.headlineMedium,
            color = GreenPrimary
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Información usuario
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(28.dp)
        ) {

            Column(
                modifier = Modifier.padding(24.dp)
            ) {

                Row {

                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = GreenPrimary
                    )

                    Spacer(
                        modifier = Modifier.width(10.dp)
                    )

                    Text(
                        text = "Información usuario",
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        name = it
                    },
                    label = {
                        Text("Nombre")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp)
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                OutlinedTextField(
                    value = currentUser?.email ?: "",
                    onValueChange = {},
                    enabled = false,
                    label = {
                        Text("Correo")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp)
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                OutlinedTextField(
                    value = role,
                    onValueChange = {},
                    enabled = false,
                    label = {
                        Text("Rol")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp)
                )

                Spacer(
                    modifier = Modifier.height(24.dp)
                )

                // Guardar cambios
                Button(
                    onClick = {

                        val userId =
                            currentUser?.uid ?: ""

                        db.collection("users")
                            .document(userId)
                            .update(
                                "name",
                                name
                            )
                            .addOnSuccessListener {

                                Toast.makeText(
                                    navController.context,
                                    "Datos actualizados",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GreenPrimary
                    )
                ) {

                    Icon(
                        imageVector = Icons.Default.Save,
                        contentDescription = null
                    )

                    Spacer(
                        modifier = Modifier.width(8.dp)
                    )

                    Text("Guardar cambios")
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Información aplicación
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(28.dp)
        ) {

            Column(
                modifier = Modifier.padding(24.dp)
            ) {

                Row {

                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = GreenPrimary
                    )

                    Spacer(
                        modifier = Modifier.width(10.dp)
                    )

                    Text(
                        text = "Acerca de la app",
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                Text(
                    text =
                        "EcoMarket es una aplicación móvil " +
                                "desarrollada para la compra " +
                                "de productos ecológicos y orgánicos."
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                Text(
                    text = "Versión 1.0"
                )
            }
        }
    }
}