package com.nicholas.application_test_hive.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nicholas.application_test_hive.R
import com.nicholas.application_test_hive.viewmodel.HabitViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: HabitViewModel
    private lateinit var adapter: HabitAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[HabitViewModel::class.java]

        val recyclerView = findViewById<RecyclerView>(R.id.habitRecyclerView)
        val syncButton = findViewById<Button>(R.id.btnSync)
        val addButton = findViewById<Button>(R.id.btnAddHabit)

        adapter = HabitAdapter(emptyList()) { habit ->
            viewModel.markDone(habit)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        syncButton.setOnClickListener {
            viewModel.loadHabits()
        }

        addButton.setOnClickListener {
            val intent = Intent(this, AddHabitActivity::class.java)
            startActivity(intent)
        }

        viewModel.habits.observe(this) { habits ->
            adapter.updateData(habits)
        }

        viewModel.loadHabits()
    }

    // 🔁 Refresh habits when returning from AddHabitActivity
    override fun onResume() {
        super.onResume()
        viewModel.loadHabits()
    }
}