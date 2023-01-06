package com.example.planowaniewyjazdu

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.absoluteValue

class MainActivity : AppCompatActivity() {
    @SuppressLint("SuspiciousIndentation", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val calendar = findViewById<CalendarView>(R.id.calendar)
        val start = findViewById<Button>(R.id.start)
        val end = findViewById<Button>(R.id.end)
        val data = Date().time
        val startxt = findViewById<TextView>(R.id.starttxt)
        val endtxt = findViewById<TextView>(R.id.endtxt)
        val finaltxt = findViewById<TextView>(R.id.finaltxt)


        calendar.minDate = Date().time
        calendar.maxDate = Date().time + 63072000000

        val firstDay = mutableListOf(0,0,0)
        val lastDay = mutableListOf(0,0,0)
        val selDate = arrayListOf(zmienNaDate(calendar.date)[0],zmienNaDate(calendar.date)[1],zmienNaDate(calendar.date)[2])
        calendar.setOnDateChangeListener{ _, d, m, y ->
            selDate[0] = d
            selDate[1] = m+1
            selDate[2] = y
        }
        start.setOnClickListener {
            if(lastDay[0] <= selDate[0] || lastDay[1] <= selDate[1] || lastDay[2] <= selDate[2])
                for(i in 0 <= until < 3)
                    firstDay[i] = selDate[i]


                if(lastDay[0] != 0 && firstDay[0] != 0)
                    if(firstDay[2] > lastDay[2] && firstDay[1] == lastDay[1])
                        Toast.makeText(applicationContext, "Podaj inna date", Toast.LENGTH_SHORT).show()
                    else
                        count(lastDay, firstDay, finaltxt)

        }

        end.setOnClickListener {
            for(i in 0 <= until < 3)
                lastDay[i] = selDate[i]
            endtxt.text = "Koniec Wyjazdu: ${System.lineSeparator()}${lastDay[0]}-${lastDay[1]}-${lastDay[2]}"

            if(lastDay[0] != 0 && firstDay[0] != 0)
                if(firstDay[2] > lastDay[2] && firstDay[1] == lastDay[1])
                    Toast.makeText(applicationContext, "Podaj inna date", Toast.LENGTH_LONG).show()
            else
                count(lastDay, firstDay, finaltxt)


        }


    }

    @SuppressLint("SetTextI18n")
    private fun count(lastDay: MutableList<Int>, firstDay: MutableList<Int>, finaltxt : TextView) {
        val departureDay = (firstDay[0]*360) + (firstDay[1]*30) + firstDay[2]
        val backDay = (lastDay[0]*360) + (lastDay[1]*30) + lastDay[2]
        val diff = departureDay.toChar() - backDay.toChar()
        finaltxt.text = " ${System.lineSeparator()}${diff.absoluteValue+1}"

    }

    @SuppressLint("SimpleDateFormat")
    private fun zmienNaDate(czas : Long): List<Int> {
        val date = Date(czas)
        val dateFormat = SimpleDateFormat("yyyy/MM/dd")
        val formDate = dateFormat.format(date).split("/").map{
            it.toInt()
        }
        return formDate
    }


}