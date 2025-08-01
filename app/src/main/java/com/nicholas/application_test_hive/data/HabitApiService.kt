package com.nicholas.application_test_hive.data

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Body
import retrofit2.http.Path

interface HabitApiService {
    @GET("habits")
    suspend fun getHabits(): List<Habit>

    @POST("habits")
    suspend fun addHabit(@Body habit: Habit): Habit

    @PUT("habits/{id}")
    suspend fun markHabitDone(@Path("id") id: Int, @Body habit: Habit): Habit
}