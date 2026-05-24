package com.ecomarket.app.ui.screens

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.ecomarket.app.ui.theme.GreenPrimary
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun PaymentScreen(navController: NavController) {

    val db = FirebaseFirestore.getInstance()

    val auth = FirebaseAuth.getInstance()

    val context = LocalContext.current

    // Cliente utilizado para obtener ubicación GPS
    val fusedLocationClient =
        LocationServices.getFusedLocationProviderClient(context)

    // Campos tarjeta
    var cardName by remember {
        mutableStateOf("")
    }

    var cardNumber by remember {
        mutableStateOf("")
    }

    // Texto ubicación
    var locationText by remember {
        mutableStateOf("Ubicación no obtenida")
    }

    // Solicitud permisos ubicación
    val locationPermissionLauncher =
        rememberLauncherForActivityResult(
            contract =
                ActivityResultContracts.RequestPermission()
        ) { isGranted ->

            if (isGranted) {

                getCurrentLocation(
                    fusedLocationClient
                ) { location ->

                    if (location != null) {

                        locationText =
                            "Lat: ${location.latitude}, " +
                                    "Lng: ${location.longitude}"

                    } else {

                        locationText =
                            "No se pudo obtener ubicación"
                    }
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

        // Título
        Text(
            text = "Método de pago",
            style = MaterialTheme.typography.headlineMedium,
            color = GreenPrimary
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Nombre titular
        OutlinedTextField(
            value = cardName,
            onValueChange = {
                cardName = it
            },
            label = {
                Text("Nombre titular")
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Número tarjeta
        OutlinedTextField(
            value = cardNumber,
            onValueChange = {
                cardNumber = it
            },
            label = {
                Text("Número tarjeta")
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botón obtener ubicación
        Button(
            onClick = {

                val permissionCheck =
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )

                if (
                    permissionCheck ==
                    PackageManager.PERMISSION_GRANTED
                ) {

                    getCurrentLocation(
                        fusedLocationClient
                    ) { location ->

                        if (location != null) {

                            locationText =
                                "Lat: ${location.latitude}, " +
                                        "Lng: ${location.longitude}"

                        } else {

                            locationText =
                                "No se pudo obtener ubicación"
                        }
                    }

                } else {

                    locationPermissionLauncher.launch(
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Gray
            )
        ) {

            Text("Obtener ubicación")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Texto ubicación actual
        Text(
            text = locationText
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Botón pago
        Button(
            onClick = {

                val userId =
                    auth.currentUser?.uid ?: ""

                if (
                    cardName.isNotEmpty() &&
                    cardNumber.isNotEmpty()
                ) {

                    // Obtiene productos carrito
                    db.collection("carts")
                        .document(userId)
                        .collection("items")
                        .get()
                        .addOnSuccessListener { result ->

                            for (document in result.documents) {

                                val order = hashMapOf(

                                    "name" to
                                            document.getString("name"),

                                    "price" to
                                            document.getString("price"),

                                    "imageUrl" to
                                            document.getString("imageUrl"),

                                    "status" to "Pendiente",

                                    "location" to locationText
                                )

                                // Guarda orden
                                db.collection("orders")
                                    .document(userId)
                                    .collection("items")
                                    .add(order)
                            }

                            // Limpia carrito
                            for (document in result.documents) {

                                db.collection("carts")
                                    .document(userId)
                                    .collection("items")
                                    .document(document.id)
                                    .delete()
                            }

                            Toast.makeText(
                                context,
                                "Compra realizada correctamente",
                                Toast.LENGTH_SHORT
                            ).show()

                            navController.navigate("orders")
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

            Text("Pagar ahora")
        }
    }
}

// Función utilizada para obtener ubicación GPS
@SuppressLint("MissingPermission")
fun getCurrentLocation(
    fusedLocationClient:
    com.google.android.gms.location.FusedLocationProviderClient,
    onLocationReceived: (Location?) -> Unit
) {

    fusedLocationClient.lastLocation
        .addOnSuccessListener { location ->

            onLocationReceived(location)
        }
}