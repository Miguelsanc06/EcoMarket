package com.ecomarket.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ecomarket.app.data.products
import com.ecomarket.app.ui.components.BottomBar
import com.ecomarket.app.ui.theme.GreenPrimary
import com.ecomarket.app.ui.theme.GreenSecondary

@Composable
fun HomeScreen(navController: NavController) {

    var search by remember { mutableStateOf("") }

    Scaffold(
        bottomBar = {
            BottomBar(navController)
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F7FA))
                .padding(padding)
                .padding(20.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column {

                    Text(
                        text = "Hola 👋",
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Text(
                        text = "EcoMarket",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = GreenPrimary
                    )
                }

                IconButton(
                    onClick = {
                        navController.navigate("profile")
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = GreenPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = search,
                onValueChange = { search = it },
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

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                GreenPrimary,
                                GreenSecondary
                            )
                        )
                    )
                    .padding(24.dp)
            ) {

                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxHeight()
                ) {

                    Column {

                        Text(
                            text = "Compra productos",
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Orgánicos y saludables 🌱",
                            color = Color.White
                        )
                    }

                    Button(
                        onClick = {
                            navController.navigate("catalog")
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            text = "Ver catálogo",
                            color = GreenPrimary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "Productos destacados",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(18.dp))

            LazyRow {

                items(products) { product ->

                    Card(
                        modifier = Modifier
                            .width(220.dp)
                            .padding(end = 16.dp)
                            .clickable {
                                navController.navigate("detail/${product.id}")
                            },
                        shape = RoundedCornerShape(24.dp),
                        elevation = CardDefaults.cardElevation(6.dp)
                    ) {

                        Column {

                            AsyncImage(
                                model = product.image,
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
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = product.description,
                                    maxLines = 2,
                                    style = MaterialTheme.typography.bodySmall
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                Text(
                                    text = product.price,
                                    color = GreenPrimary,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(modifier = Modifier.height(14.dp))

                                Button(
                                    onClick = {
                                        navController.navigate("cart")
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = GreenSecondary
                                    ),
                                    shape = RoundedCornerShape(16.dp)
                                ) {
                                    Text("Agregar")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}