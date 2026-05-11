package com.ecomarket.app.data

data class Product(
    val id: Int,
    val name: String,
    val price: String,
    val description: String,
    val image: String,
    val category: String,
    val rating: Double
)