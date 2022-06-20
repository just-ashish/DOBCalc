package com.ashish.dobcalc

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private var tvSelectedDate : TextView? = null
    private var tvSelectedDateInMinute : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnSelectDate : Button = findViewById(R.id.btnDatePicker)
        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvSelectedDateInMinute = findViewById(R.id.tvAgeResult)

        btnSelectDate.setOnClickListener{
            clickDatePicker()
        }
    }

    private fun clickDatePicker(){
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(this, {_, selectedYear, selectedMonth, selectedDayOfMonth ->
            Toast.makeText(this,
                "Selected Date is $selectedYear.${selectedMonth+1}.$selectedDayOfMonth",
                Toast.LENGTH_LONG).show()
            val selectedDate = "$selectedYear/${selectedMonth+1}/$selectedDayOfMonth"
            tvSelectedDate?.text = selectedDate

            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

            val theDate = sdf.parse(selectedDate)
            theDate?.let {
                val selectedDateInMinute = theDate.time/60000
                val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                currentDate?.let {
                    val currentDateInMinute = currentDate.time/60000
                    val differenceInMinute = currentDateInMinute - selectedDateInMinute

                    tvSelectedDateInMinute?.text = differenceInMinute.toString()
                }
            }

        },
            year,
            month,
            day
        )
        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()
    }
}