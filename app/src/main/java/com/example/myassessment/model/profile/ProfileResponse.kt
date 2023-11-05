package com.example.myassessment.model.profile

data class ProfileResponse(
    val address: String,
    val balance: Double,
    val city: String,
    val country: String,
    val currency: Int,
    val currentTradesCount: Int,
    val currentTradesVolume: Double,
    val equity: Double,
    val freeMargin: Double,
    val isAnyOpenTrades: Boolean,
    val isSwapFree: Boolean,
    val leverage: Int,
    val name: String,
    val phone: String,
    val totalTradesCount: Int,
    val totalTradesVolume: Double,
    val type: Int,
    val verificationLevel: Int,
    val zipCode: String
)