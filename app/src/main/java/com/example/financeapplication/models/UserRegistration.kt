package com.example.financeapplication.models

data class UserRegistration(
    val username: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
)