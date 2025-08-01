package com.nicholas.application_test_hive.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.nicholas.application_test_hive.R
import com.nicholas.application_test_hive.viewmodel.HabitViewModel

class AddHabitActivity : AppCompatActivity() {

    private lateinit var viewModel: HabitViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_habit)

        viewModel = ViewModelProvider(this)[HabitViewModel::class.java]

        val nameField = findViewById<EditText>(R.id.editHabitName)
        val descriptionField = findViewById<EditText>(R.id.editHabitDescription)
        val saveButton = findViewById<Button>(R.id.btnSaveHabit)

        saveButton.setOnClickListener {
            val name = nameField.text.toString().trim()
            val description = descriptionField.text.toString().trim()
            if (name.isNotEmpty()) {
                viewModel.addHabit(name, description)
                Toast.makeText(this, "Saving habit...", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Name is required", Toast.LENGTH_SHORT).show()
            }
        }

    }
}

