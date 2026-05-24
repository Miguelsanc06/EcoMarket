package com.ecomarket.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ecomarket.app.models.Product
import com.ecomarket.app.ui.theme.GreenPrimary
import com.google.firebase.firestore.FirebaseFirestore
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProductDetailScreen(
    navController: NavController,
    productId: String
) {

    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()

    val context = LocalContext.current

    // Producto seleccionado
    var product by remember {
        mutableStateOf(Product())
    }

    // Carga producto real
    LaunchedEffect(productId) {

        if (productId.isNotEmpty()) {

            db.collection("products")
                .document(productId)
                .get()
                .addOnSuccessListener { document ->

                    if (document.exists()) {

                        product = Product(
                            id = document.id,
                            name = document.getString("name") ?: "",
                            description = document.getString("description") ?: "",
                            price = document.getString("price") ?: "",
                            imageUrl = document.getString("imageUrl") ?: ""
                        )
                    }
                }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F7FA))
            .padding(top = 40.dp)
            ) {

                AsyncImage(
                    model = product.imageUrl,
                    contentDescription = product.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(320.dp),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    Text(
                        text = product.name,
                        style = MaterialTheme.typography.headlineMedium
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = product.description
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "$ ${product.price}",
                        style = MaterialTheme.typography.titleLarge,
                        color = GreenPrimary
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {

                            val userId =
                                auth.currentUser?.uid ?: ""

                            if (userId.isNotEmpty()) {

                                val cartItem = hashMapOf(

                                    "name" to product.name,
                                    "price" to product.price,
                                    "imageUrl" to product.imageUrl
                                )

                                // Guarda producto en carrito
                                db.collection("carts")
                                    .document(userId)
                                    .collection("items")
                                    .add(cartItem)
                                    .addOnSuccessListener {

                                        Toast.makeText(
                                            context,
                                            "Producto agregado al carrito",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                        navController.navigate("cart")
                                    }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = GreenPrimary
                        )
                    ) {

                        Text("Agregar al carrito")
                    }
                }
            }
}