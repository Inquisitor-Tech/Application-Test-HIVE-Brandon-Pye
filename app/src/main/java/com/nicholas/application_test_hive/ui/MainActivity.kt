package com.nicholas.application_test_hive.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.nicholas.application_test_hive.R
import com.nicholas.application_test_hive.viewmodel.HabitViewModel
import com.nicholas.application_test_hive.viewmodel.HabitViewModel.FilterType

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: HabitViewModel
    private lateinit var adapter: HabitAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[HabitViewModel::class.java]

        val recyclerView = findViewById<RecyclerView>(R.id.habitRecyclerView)
        val syncButton = findViewById<MaterialButton>(R.id.btnSync)
        val addButton = findViewById<MaterialButton>(R.id.btnAddHabit)
        val filterSpinner = findViewById<Spinner>(R.id.filterSpinner)

        adapter = HabitAdapter(emptyList()) { habit ->
            viewModel.markDone(habit)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Spinner setup
        val filterOptions = listOf("All", "Done", "Not Done")
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, filterOptions)
        filterSpinner.adapter = spinnerAdapter

        filterSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (filterOptions[position]) {
                    "All" -> viewModel.setFilter(FilterType.ALL)
                    "Done" -> viewModel.setFilter(FilterType.DONE)
                    "Not Done" -> viewModel.setFilter(FilterType.NOT_DONE)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

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

    override fun onResume() {
        super.onResume()
        viewModel.loadHabits()
    }
}
