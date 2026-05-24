package com.ecomarket.app.ui.screens

import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import com.ecomarket.app.ui.theme.GreenPrimary
import com.ecomarket.app.ui.theme.GreenSecondary
import com.google.firebase.auth.FirebaseAuth
import com.ecomarket.app.utils.getActivity
import android.app.Activity
import java.util.concurrent.Executor

@Composable
fun LoginScreen(navController: NavController) {

    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()

    // Variables para correo y contraseña
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F7FA))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "EcoMarket 🌱",
            style = MaterialTheme.typography.headlineLarge,
            color = GreenPrimary
        )

        Spacer(modifier = Modifier.height(50.dp))

        // Campo correo
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            label = {
                Text("Correo electrónico")
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

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

        Spacer(modifier = Modifier.height(30.dp))

        // Inicio de sesión con Firebase
        Button(
            onClick = {

                if (email.isNotEmpty() && password.isNotEmpty()) {

                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->

                            if (task.isSuccessful) {

                                // Obtiene usuario autenticado
                                val userId = auth.currentUser?.uid

                                if (userId != null) {

                                    // Consulta datos del usuario en Firestore
                                    com.google.firebase.firestore.FirebaseFirestore
                                        .getInstance()
                                        .collection("users")
                                        .document(userId)
                                        .get()
                                        .addOnSuccessListener { document ->

                                            val role =
                                                document.getString("role")

                                            Toast.makeText(
                                                context,
                                                "Bienvenido",
                                                Toast.LENGTH_SHORT
                                            ).show()

                                            // Navegación según rol
                                            when (role) {

                                                "Administrador" -> {
                                                    navController.navigate(
                                                        "adminDashboard"
                                                    )
                                                }

                                                "Vendedor" -> {
                                                    navController.navigate(
                                                        "sellerDashboard"
                                                    )
                                                }

                                                else -> {
                                                    navController.navigate(
                                                        "home"
                                                    )
                                                }
                                            }
                                        }
                                }

                            } else {

                                Toast.makeText(
                                    context,
                                    "Credenciales incorrectas",
                                    Toast.LENGTH_SHORT
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

            Text("Iniciar sesión")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón autenticación biométrica
        Button(
            onClick = {

                val activity = context.getActivity()

                if (activity == null) {

                    Toast.makeText(
                        context,
                        "La biometría no está disponible",
                        Toast.LENGTH_SHORT
                    ).show()

                    return@Button
                }

                val biometricManager =
                    BiometricManager.from(context)

                when (
                    biometricManager.canAuthenticate(
                        BiometricManager.Authenticators.BIOMETRIC_WEAK                    )
                ) {

                    BiometricManager.BIOMETRIC_SUCCESS -> {

                        val executor =
                            ContextCompat.getMainExecutor(context)

                        val biometricPrompt =
                            BiometricPrompt(
                                activity,
                                executor,
                                object :
                                    BiometricPrompt.AuthenticationCallback() {

                                    override fun onAuthenticationSucceeded(
                                        result: BiometricPrompt.AuthenticationResult
                                    ) {

                                        super.onAuthenticationSucceeded(result)

                                        Toast.makeText(
                                            context,
                                            "Ingreso biométrico exitoso",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                        navController.navigate("home")
                                    }

                                    override fun onAuthenticationFailed() {

                                        super.onAuthenticationFailed()

                                        Toast.makeText(
                                            context,
                                            "Huella no reconocida",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }

                                    override fun onAuthenticationError(
                                        errorCode: Int,
                                        errString: CharSequence
                                    ) {

                                        super.onAuthenticationError(
                                            errorCode,
                                            errString
                                        )

                                        Toast.makeText(
                                            context,
                                            errString.toString(),
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            )

                        val promptInfo =
                            PromptInfo.Builder()
                                .setTitle("Autenticación biométrica")
                                .setSubtitle("Ingresa con tu huella")
                                .setAllowedAuthenticators(
                                    BiometricManager.Authenticators.BIOMETRIC_WEAK
                                )
                                .build()

                        biometricPrompt.authenticate(promptInfo)
                    }

                    else -> {

                        Toast.makeText(
                            context,
                            "El dispositivo no tiene biometría configurada",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = GreenSecondary
            )
        ) {

            Icon(
                imageVector = Icons.Default.Fingerprint,
                contentDescription = null
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text("Ingresar con huella")
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Navega al registro
        TextButton(
            onClick = {
                navController.navigate("register")
            }
        ) {

            Text("Crear cuenta")
        }

        // Recuperación de contraseña
        TextButton(
            onClick = {

                if (email.isNotEmpty()) {

                    auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener { task ->

                            if (task.isSuccessful) {

                                Toast.makeText(
                                    context,
                                    "Correo de recuperación enviado",
                                    Toast.LENGTH_LONG
                                ).show()

                            } else {

                                Toast.makeText(
                                    context,
                                    task.exception?.message
                                        ?: "Error al enviar correo",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                }
            }
        ) {

            Text("Recuperar contraseña")
        }
    }
}