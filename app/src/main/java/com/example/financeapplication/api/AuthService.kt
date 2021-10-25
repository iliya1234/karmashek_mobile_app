package com.example.financeapplication.api

import com.example.financeapplication.models.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthService {

    @Headers("Content-Type: application/json")
    @POST("login")
    suspend fun login(@Body user: UserAuthRequest): Response<UserAuthResponse>

    @Headers("Content-Type: application/json")
    @POST("register")
    suspend fun registration(@Body user: UserRegistration): Response<UserAuthResponse>
}