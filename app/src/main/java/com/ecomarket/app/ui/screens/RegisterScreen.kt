package com.ecomarket.app.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ecomarket.app.ui.theme.GreenPrimary
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun RegisterScreen(navController: NavController) {

    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    // Variables del formulario
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Rol seleccionado
    var selectedRole by remember {
        mutableStateOf("Comprador")
    }

    val roles = listOf(
        "Comprador",
        "Vendedor",
        "Administrador"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F7FA))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Crear cuenta",
            style = MaterialTheme.typography.headlineMedium,
            color = GreenPrimary
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Campo nombre
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

        Spacer(modifier = Modifier.height(16.dp))

        // Campo correo
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            label = {
                Text("Correo")
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo contraseña
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            label = {
                Text("Contraseña")
            },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Selección de roles
        Text(
            text = "Selecciona un rol"
        )

        Spacer(modifier = Modifier.height(10.dp))

        roles.forEach { role ->

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                RadioButton(
                    selected = selectedRole == role,
                    onClick = {
                        selectedRole = role
                    }
                )

                Text(text = role)
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Registro con Firebase
        Button(
            onClick = {

                if (
                    name.isNotEmpty() &&
                    email.isNotEmpty() &&
                    password.isNotEmpty()
                ) {

                    auth.createUserWithEmailAndPassword(
                        email,
                        password
                    ).addOnCompleteListener { task ->

                        if (task.isSuccessful) {

                            val userId =
                                auth.currentUser?.uid

                            // Datos del usuario
                            val user = hashMapOf(
                                "name" to name,
                                "email" to email,
                                "role" to selectedRole
                            )

                            // Guarda usuario en Firestore
                            if (userId != null) {

                                db.collection("users")
                                    .document(userId)
                                    .set(user)
                            }

                            Toast.makeText(
                                navController.context,
                                "Usuario registrado",
                                Toast.LENGTH_SHORT
                            ).show()

                            navController.navigate("login")

                        } else {

                            Toast.makeText(
                                navController.context,
                                task.exception?.message ?: "Error desconocido",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = GreenPrimary
            )
        ) {

            Text("Registrarse")
        }

        Spacer(modifier = Modifier.height(20.dp))

        TextButton(
            onClick = {
                navController.navigate("login")
            }
        ) {

            Text("Ya tengo cuenta")
        }
    }
}