package com.solopov.jetpack_compose_practice


data class Place(
    val imageId: Int,
    val name: String,
    val region: String,
    val country: String,
    var rating: Float,
    val fullName: String,
    val price: Int,
    val travelTimeHours: Float,
    val currentTempCelsius: Int,
    val description: String,
)
