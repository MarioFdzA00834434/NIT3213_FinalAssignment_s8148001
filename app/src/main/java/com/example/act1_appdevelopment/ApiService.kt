package com.example.act1_appdevelopment

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("footscray/auth")
    fun login(@Body credentials: UserCredentials): Call<LoginResponse>

    @GET("dashboard/{keypass}")
    fun getDashboard(@Path("keypass") keypass: String): Call<DashboardResponse>
}
