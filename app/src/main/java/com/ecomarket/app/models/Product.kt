package com.ecomarket.app.models

// Modelo de producto utilizado en Firestore
data class Product(

    val id: String = "",

    val name: String = "",

    val description: String = "",

    val price: String = "",

    val imageUrl: String = ""
)