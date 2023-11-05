package com.example.myassessment.model.userlist

data class UserResponseItem(
    val comment: Any,
    val currentPrice: Double,
    val digits: Int,
    val login: Int,
    val openPrice: Double,
    val openTime: String,
    val profit: Double,
    val sl: Double,
    val swaps: Double,
    val symbol: String,
    val ticket: Int,
    val tp: Double,
    val type: Int,
    val volume: Double
)