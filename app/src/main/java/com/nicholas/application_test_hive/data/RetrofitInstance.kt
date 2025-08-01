package com.nicholas.application_test_hive.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://688cb5becd9d22dda5ce2fad.mockapi.io/habits/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: HabitApiService = retrofit.create(HabitApiService::class.java)
}
