package com.ecomarket.app.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ecomarket.app.models.Product
import com.ecomarket.app.ui.theme.GreenPrimary
import com.google.firebase.firestore.FirebaseFirestore
import androidx.compose.ui.platform.LocalContext

@Composable
fun ProductsManagementScreen() {

    val db = FirebaseFirestore.getInstance()
    val context = LocalContext.current

    // Lista de productos
    var products by remember {
        mutableStateOf(listOf<Product>())
    }

    // Variables formulario
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }

    // Carga productos reales
    LaunchedEffect(Unit) {

        db.collection("products")
            .addSnapshotListener { result, _ ->

                if (result != null) {

                    val productList =
                        mutableListOf<Product>()

                    for (document in result.documents) {

                        val product = Product(
                            id = document.id,
                            name = document.getString("name") ?: "",
                            description = document.getString("description") ?: "",
                            price = document.getString("price") ?: "",
                            imageUrl = document.getString("imageUrl") ?: ""
                        )

                        productList.add(product)
                    }

                    products = productList
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
            text = "Gestión de productos",
            style = MaterialTheme.typography.headlineMedium,
            color = GreenPrimary
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Nombre producto
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

        Spacer(modifier = Modifier.height(12.dp))

        // Descripción
        OutlinedTextField(
            value = description,
            onValueChange = {
                description = it
            },
            label = {
                Text("Descripción")
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Precio
        OutlinedTextField(
            value = price,
            onValueChange = {
                price = it
            },
            label = {
                Text("Precio")
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // URL imagen
        OutlinedTextField(
            value = imageUrl,
            onValueChange = {
                imageUrl = it
            },
            label = {
                Text("URL imagen")
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Botón agregar producto
        Button(
            onClick = {

                if (
                    name.isNotEmpty() &&
                    description.isNotEmpty() &&
                    price.isNotEmpty() &&
                    imageUrl.isNotEmpty()
                ) {

                    val product = hashMapOf(

                        "name" to name,
                        "description" to description,
                        "price" to price,
                        "imageUrl" to imageUrl
                    )

                    // Guarda producto en Firestore
                    db.collection("products")
                        .add(product)
                        .addOnSuccessListener {

                            Toast.makeText(
                                context,
                                "Producto agregado",
                                Toast.LENGTH_SHORT
                            ).show()

                            // Limpia formulario
                            name = ""
                            description = ""
                            price = ""
                            imageUrl = ""
                        }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = GreenPrimary
            ),
            shape = RoundedCornerShape(18.dp)
        ) {

            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text("Agregar producto")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Productos registrados",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {

            items(products) { product ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    shape = RoundedCornerShape(24.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        AsyncImage(
                            model = product.imageUrl,
                            contentDescription = product.name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                        )

                        Spacer(
                            modifier = Modifier.height(12.dp)
                        )

                        Text(
                            text = product.name,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(
                            modifier = Modifier.height(6.dp)
                        )

                        Text(product.description)

                        Spacer(
                            modifier = Modifier.height(6.dp)
                        )

                        Text(
                            text = "$ ${product.price}",
                            color = GreenPrimary
                        )

                        Spacer(
                            modifier = Modifier.height(12.dp)
                        )

                        Button(
                            onClick = {

                                // Elimina producto
                                db.collection("products")
                                    .document(product.id)
                                    .delete()
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