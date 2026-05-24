package com.ecomarket.app.models

// Modelo utilizado para las órdenes del usuario
data class Order(

    val id: String = "",

    val name: String = "",

    val price: String = "",

    val imageUrl: String = "",

    val status: String = "Pendiente"
)