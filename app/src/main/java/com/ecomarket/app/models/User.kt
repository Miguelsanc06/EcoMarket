package com.ecomarket.app.models

// Modelo de usuario almacenado en Firestore
data class User(

    val id: String = "",
    val name: String = "",
    val email: String = "",
    val role: String = ""
)