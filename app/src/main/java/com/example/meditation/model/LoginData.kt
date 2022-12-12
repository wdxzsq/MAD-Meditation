package com.example.meditation.model

data class LoginData(
    val email: String,
    val password: String,
)

data class gLoginData(
    val id: String,
    val email: String,
    val nickName: String,
    val avatar: String,
    val token: String,
)
data class eLoginData(
    val error: String,
    val success: String,
)
