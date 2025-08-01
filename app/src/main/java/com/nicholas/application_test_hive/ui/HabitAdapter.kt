package com.nicholas.application_test_hive.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nicholas.application_test_hive.R
import com.nicholas.application_test_hive.data.Habit

class HabitAdapter(
    private var habits: List<Habit>,
    private val onMarkDoneClicked: (Habit) -> Unit
) : RecyclerView.Adapter<HabitAdapter.HabitViewHolder>() {

    class HabitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.textHabitName)
        val description: TextView = itemView.findViewById(R.id.textHabitDescription)
        val lastCompleted: TextView = itemView.findViewById(R.id.textLastCompleted)
        val btnMarkDone: Button = itemView.findViewById(R.id.btnMarkDone)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_habit, parent, false)
        return HabitViewHolder(view)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habit = habits[position]

        holder.name.text = habit.name
        holder.description.text = habit.description

        val displayDate = habit.lastCompletedDate?.takeIf { it.isNotBlank() }?.take(10) ?: "Never"


        holder.lastCompleted.text = "Last Completed: $displayDate"

        holder.btnMarkDone.setOnClickListener {
            onMarkDoneClicked(habit)
        }
    }

    override fun getItemCount(): Int = habits.size

    fun updateData(newHabits: List<Habit>) {
        habits = newHabits
        notifyDataSetChanged()
    }
}
