package com.example.ageinminute

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.Button
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private var tvSelectedDate : TextView? = null
    private var tvAgeInMinutes : TextView? = null
    private var tvTextSelectedDate : TextView? = null
    private var tvTextAgeInMinutes : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDatePicker: Button = findViewById(R.id.btnDatePicker)
        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvAgeInMinutes = findViewById(R.id.tvAgeInMinutes)
        tvTextSelectedDate = findViewById(R.id.tvTextSelectedDate)
        tvTextAgeInMinutes = findViewById(R.id.tvTextAgeInMinutes)

        btnDatePicker.setOnClickListener {
            clickDatePicker()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun clickDatePicker() {
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                // set the selected date into text view
                val selectedDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
                tvSelectedDate?.text = selectedDate
                tvTextSelectedDate?.text = "Selected Date"

                // calculate age in minutes
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val theDate = sdf.parse(selectedDate)
                theDate?.let {
                    val selectedDateInMinute = (theDate.time) / 60000
                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                    currentDate?.let {
                        val currentDateInMinute = (currentDate.time) / 60000
                        val differenceInMinute = currentDateInMinute - selectedDateInMinute
                        tvAgeInMinutes?.text = "$differenceInMinute"
                        tvTextAgeInMinutes?.text = "Age in Minutes"
                    }
                }
            },
            year, month, day
        )
        // at least 1 day old is required
        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()
    }
}