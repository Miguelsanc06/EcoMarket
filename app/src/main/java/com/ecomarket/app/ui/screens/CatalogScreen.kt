package com.ecomarket.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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

@Composable
fun CatalogScreen(navController: NavController) {

    val db = FirebaseFirestore.getInstance()

    // Lista de productos reales
    var products by remember {
        mutableStateOf(listOf<Product>())
    }

    // Carga productos desde Firestore
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
            text = "Catálogo",
            style = MaterialTheme.typography.headlineMedium,
            color = GreenPrimary
        )

        Spacer(modifier = Modifier.height(24.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {

            items(products) { product ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {

                            // Navega al detalle
                            navController.navigate(
                                "detail/${product.id}"
                            )
                        },
                    shape = RoundedCornerShape(24.dp)
                ) {

                    Column {

                        AsyncImage(
                            model = product.imageUrl,
                            contentDescription = product.name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp),
                            contentScale = ContentScale.Crop
                        )

                        Column(
                            modifier = Modifier.padding(14.dp)
                        ) {

                            Text(
                                text = product.name,
                                style = MaterialTheme.typography.titleMedium
                            )

                            Spacer(
                                modifier = Modifier.height(6.dp)
                            )

                            Text(
                                text = "$ ${product.price}",
                                color = GreenPrimary
                            )
                        }
                    }
                }
            }
        }
    }
}