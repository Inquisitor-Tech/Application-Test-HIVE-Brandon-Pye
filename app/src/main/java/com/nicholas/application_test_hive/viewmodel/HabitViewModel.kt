package com.nicholas.application_test_hive.viewmodel

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.nicholas.application_test_hive.data.Habit
import com.nicholas.application_test_hive.repository.HabitRepository
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter


class HabitViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = HabitRepository()
    private val _habits = MutableLiveData<List<Habit>>()
    val habits: LiveData<List<Habit>> = _habits

    fun loadHabits() {
        viewModelScope.launch {
            try {
                _habits.value = repository.fetchHabits()
            } catch (e: Exception) {

                _habits.value = emptyList()
            }
        }
    }

    fun addHabit(name: String, description: String) {
        val newHabit = Habit(
            id = 0,
            name = name,
            description = description,
            lastCompletedDate = "Never/Not Done"
        )

        viewModelScope.launch {
            try {
                repository.createHabit(newHabit)
                loadHabits()
            } catch (e: Exception) {
                Log.e("HabitViewModel", "Failed to add habit", e)
            }
        }
    }


    fun markDone(habit: Habit) {
        val today = LocalDate.now().format(DateTimeFormatter.ISO_DATE)
        val updatedHabit = habit.copy(lastCompletedDate = today)

        viewModelScope.launch {
            try {
                repository.markHabitAsDone(updatedHabit)
                loadHabits()
            } catch (e: Exception) {

            }
        }
    }
}
