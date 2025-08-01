package com.nicholas.application_test_hive.repository

import com.nicholas.application_test_hive.data.Habit
import com.nicholas.application_test_hive.data.RetrofitInstance

class HabitRepository {
    private val api = RetrofitInstance.api

    suspend fun fetchHabits() = api.getHabits()
    suspend fun createHabit(habit: Habit) = api.addHabit(habit)
    suspend fun markHabitAsDone(habit: Habit) = api.markHabitDone(habit.id, habit)
}