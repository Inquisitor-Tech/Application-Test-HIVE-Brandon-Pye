package com.nicholas.application_test_hive.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.nicholas.application_test_hive.data.Habit
import com.nicholas.application_test_hive.repository.HabitRepository
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

class HabitViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = HabitRepository()
    private val allHabits = mutableListOf<Habit>()
    private val _habits = MutableLiveData<List<Habit>>()
    val habits: LiveData<List<Habit>> = _habits

    enum class FilterType { ALL, DONE, NOT_DONE }
    private var currentFilter = FilterType.ALL

    fun loadHabits() {
        viewModelScope.launch {
            try {
                allHabits.clear()
                allHabits.addAll(repository.fetchHabits())
                applyFilter()
            } catch (e: Exception) {
                Log.e("HabitViewModel", "Error loading habits", e)
                _habits.value = emptyList()
            }
        }
    }

    fun addHabit(name: String, description: String) {
        val newHabit = Habit(
            id = 0,
            name = name,
            description = description,
            lastCompletedDate = "Never"
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
                Log.e("HabitViewModel", "Failed to update habit", e)
            }
        }
    }

    fun setFilter(filter: FilterType) {
        currentFilter = filter
        applyFilter()
    }

    private fun applyFilter() {
        _habits.value = when (currentFilter) {
            FilterType.ALL -> allHabits
            FilterType.DONE -> allHabits.filter { !it.lastCompletedDate.isNullOrBlank() && it.lastCompletedDate != "Never" }
            FilterType.NOT_DONE -> allHabits.filter { it.lastCompletedDate.isNullOrBlank() || it.lastCompletedDate == "Never" }
        }
    }
}
