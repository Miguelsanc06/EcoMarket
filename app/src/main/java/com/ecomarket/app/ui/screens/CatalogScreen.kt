package com.ecomarket.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ecomarket.app.data.products
import com.ecomarket.app.ui.components.BottomBar

@Composable
fun CatalogScreen(navController: NavController) {

    Scaffold(
        bottomBar = {
            BottomBar(navController)
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF7F8FA))
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            Row {

                Icon(
                    Icons.Default.Store,
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = "Catálogo",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            LazyColumn {

                items(products) { product ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 18.dp),
                        shape = RoundedCornerShape(24.dp)
                    ) {

                        Column {

                            AsyncImage(
                                model = product.image,
                                contentDescription = product.name,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(220.dp),
                                contentScale = ContentScale.Crop
                            )

                            Column(
                                modifier = Modifier.padding(18.dp)
                            ) {

                                Text(
                                    text = product.name,
                                    style = MaterialTheme.typography.titleLarge
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = product.description,
                                    color = Color.Gray
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                Text(
                                    text = product.category,
                                    color = Color(0xFF2E7D32)
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = "⭐ ${product.rating}",
                                    color = Color(0xFFFF9800)
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                Text(
                                    text = product.price,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color(0xFF2E7D32)
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                Button(
                                    onClick = {
                                        navController.navigate("detail/${product.id}")
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(16.dp)
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