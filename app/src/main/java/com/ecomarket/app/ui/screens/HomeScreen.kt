package com.ecomarket.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ecomarket.app.models.Product
import com.ecomarket.app.ui.components.BottomBar
import com.ecomarket.app.ui.theme.GreenPrimary
import com.ecomarket.app.ui.theme.GreenSecondary
import com.google.firebase.firestore.FirebaseFirestore
import androidx.compose.material.icons.automirrored.filled.List

@Composable
fun HomeScreen(navController: NavController) {

    val db = FirebaseFirestore.getInstance()

    // Lista productos reales
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

    Scaffold(

        bottomBar = {
            BottomBar(navController)
        }

    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F7FA))
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
                .padding(top = 20.dp)
        ) {

            // Título principal
            Text(
                text = "EcoMarket 🌱",
                style = MaterialTheme.typography.headlineMedium,
                color = GreenPrimary
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Barra búsqueda visual
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = {
                    Text("Buscar productos")
                },
                leadingIcon = {
                    Icon(Icons.Default.Search, null)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Banner principal
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(
                    containerColor = GreenPrimary
                )
            ) {

                Column(
                    modifier = Modifier.padding(24.dp)
                ) {

                    Text(
                        text = "Productos orgánicos",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        text = "Compra saludable y ecológica",
                        color = Color.White
                    )

                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )

                    Button(
                        onClick = {

                            navController.navigate("catalog")
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = GreenSecondary
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {

                        Icon(
                            imageVector = Icons.Default.Store,
                            contentDescription = null
                        )

                        Spacer(
                            modifier = Modifier.width(8.dp)
                        )

                        Text("Ver catálogo")
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Accesos rápidos
            Row(
                horizontalArrangement =
                    Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {

                HomeOptionCard(
                    title = "Carrito",
                    icon = Icons.Default.ShoppingCart
                ) {

                    navController.navigate("cart")
                }

                HomeOptionCard(
                    title = "Pedidos",
                    Icons.AutoMirrored.Filled.List
                ) {

                    navController.navigate("orders")
                }

                HomeOptionCard(
                    title = "Perfil",
                    icon = Icons.Default.Person
                ) {

                    navController.navigate("profile")
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Productos destacados",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Productos dinámicos
            LazyRow {

                items(products) { product ->

                    Card(
                        modifier = Modifier
                            .width(220.dp)
                            .padding(end = 16.dp)
                            .clickable {

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
                                    .height(160.dp),
                                contentScale = ContentScale.Crop
                            )

                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {

                                Text(
                                    text = product.name,
                                    style = MaterialTheme.typography.titleMedium
                                )

                                Spacer(
                                    modifier = Modifier.height(8.dp)
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

                                        navController.navigate(
                                            "detail/${product.id}"
                                        )
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = GreenSecondary
                                    ),
                                    shape = RoundedCornerShape(14.dp)
                                ) {

                                    Text("Ver producto")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// Card reutilizable del home
@Composable
fun HomeOptionCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .width(110.dp)
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(20.dp)
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = GreenPrimary
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(text = title)
        }
    }
}