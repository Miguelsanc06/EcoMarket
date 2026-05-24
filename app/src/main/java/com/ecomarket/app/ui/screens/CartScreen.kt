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
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ecomarket.app.models.CartItem
import com.ecomarket.app.ui.theme.GreenPrimary
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun CartScreen(navController: NavController) {

    val db = FirebaseFirestore.getInstance()

    val auth = FirebaseAuth.getInstance()

    // Lista carrito
    var cartItems by remember {
        mutableStateOf(listOf<CartItem>())
    }

    // Carga carrito real
    LaunchedEffect(Unit) {

        val userId =
            auth.currentUser?.uid ?: ""

        db.collection("carts")
            .document(userId)
            .collection("items")
            .addSnapshotListener { result, _ ->

                if (result != null) {

                    val itemList =
                        mutableListOf<CartItem>()

                    for (document in result.documents) {

                        val item = CartItem(
                            id = document.id,
                            name = document.getString("name") ?: "",
                            price = document.getString("price") ?: "",
                            imageUrl = document.getString("imageUrl") ?: ""
                        )

                        itemList.add(item)
                    }

                    cartItems = itemList
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
            text = "Carrito",
            style = MaterialTheme.typography.headlineMedium,
            color = GreenPrimary
        )

        Spacer(modifier = Modifier.height(24.dp))

        LazyColumn {

            items(cartItems) { item ->

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
                            model = item.imageUrl,
                            contentDescription = item.name,
                            modifier = Modifier
                                .size(100.dp),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(
                            modifier = Modifier.width(16.dp)
                        )

                        Column {

                            Text(
                                text = item.name,
                                style = MaterialTheme.typography.titleMedium
                            )

                            Spacer(
                                modifier = Modifier.height(8.dp)
                            )

                            Text(
                                text = "$ ${item.price}",
                                color = GreenPrimary
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {

                navController.navigate("payment")
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = GreenPrimary
            )
        ) {

            Text("Continuar compra")
        }
    }
}