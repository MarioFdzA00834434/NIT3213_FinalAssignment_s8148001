package com.example.act1_appdevelopment

data class DashboardResponse(
    val entities: List<Entity>,
    val entityTotal: Int
)

data class Entity(
    val destination: String,
    val country: String,
    val bestSeason: String,
    val popularAttraction: String,
    val description: String
)
