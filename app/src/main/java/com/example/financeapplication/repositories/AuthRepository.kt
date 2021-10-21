package com.example.financeapplication.repositories

import com.example.financeapplication.api.AuthService
import com.example.financeapplication.models.UserAuthRequest
import com.example.financeapplication.models.UserRegistration
import javax.inject.Inject

class AuthRepository
@Inject
constructor(private val api: AuthService)
{
    suspend fun authUser(userRequest: UserAuthRequest) = api.login(userRequest)

    suspend fun registerUser(userRequest: UserRegistration) = api.registration(userRequest)
}