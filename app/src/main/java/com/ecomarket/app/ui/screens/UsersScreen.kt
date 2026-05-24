package com.ecomarket.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ecomarket.app.models.User
import com.ecomarket.app.ui.theme.GreenPrimary
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun UsersScreen() {

    val db = FirebaseFirestore.getInstance()

    // Lista de usuarios
    var users by remember {
        mutableStateOf(listOf<User>())
    }

    // Carga usuarios desde Firestore
    LaunchedEffect(Unit) {

        db.collection("users")
            .get()
            .addOnSuccessListener { result ->

                val userList = mutableListOf<User>()

                for (document in result) {

                    val user = User(
                        id = document.id,
                        name = document.getString("name") ?: "",
                        email = document.getString("email") ?: "",
                        role = document.getString("role") ?: ""
                    )

                    userList.add(user)
                }

                users = userList
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F7FA))
            .padding(20.dp)
    ) {

        Text(
            text = "Gestión de usuarios",
            style = MaterialTheme.typography.headlineMedium,
            color = GreenPrimary
        )

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn {

            items(users) { user ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    shape = RoundedCornerShape(20.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(18.dp)
                    ) {

                        Text(
                            text = user.name,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(text = user.email)

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Rol: ${user.role}"
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = {

                                // Elimina usuario de Firestore
                                db.collection("users")
                                    .document(user.id)
                                    .delete()

                                users = users.filter {
                                    it.id != user.id
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Red
                            )
                        ) {

                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null
                            )

                            Spacer(
                                modifier = Modifier.width(8.dp)
                            )

                            Text("Eliminar")
                        }
                    }
                }
            }
        }
    }
}